import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/user/service/user.service';

@Component({
  selector: 'app-view-courses',
  templateUrl: './view-courses.component.html',
  styleUrls: ['./view-courses.component.scss']
})
export class ViewCoursesComponent implements OnInit, OnDestroy {

  @ViewChild('videoPlayer') videoElement!: ElementRef;

  constructor(private aroute: ActivatedRoute,
    private service: UserService
  ) { }

  ngOnInit(): void {
    this.aroute.queryParamMap.subscribe((res: any) => {
      if(res != null && res != undefined && res?.params != null && JSON.stringify(res?.params) != '{}') {
        this.courseId = res?.params?.courseId;
        this.service.fetchCourseUrl(Number(this.courseId)).subscribe((res: any) => {
          const restemp = res.data;
          this.service.fetchCourseProgress(Number(this.courseId)).subscribe((progress: any) => {
            this.videoUrl = restemp?.preSignedVideoUrl;
            this.courseTitle = restemp?.title;
            this.description = restemp?.description;
            this.moduleTitle = restemp?.title;
            setTimeout(() => {
              this.videoElement.nativeElement.currentTime = progress?.data?.progressTime || 0;
            }, 200);
          })
        })
      }
    })
  }

  courseId: number = 0;
  videoUrl = '';
  courseTitle = 'Java for Beginners';
  moduleTitle = 'Java Basics';
  description = 'Introduction to Java Virtual Machine, Bytecode and Runtime';
  loading = true;
  lastSentTime = 0;
  intervalRef: any;

  onMetadataLoaded(): void {
    this.loading = false;
    this.startTracking();
  }

  startTracking(): void {
    this.intervalRef = setInterval(() => {
      const currentTime = this.videoElement.nativeElement.currentTime;
      if(Math.abs(currentTime - this.lastSentTime) >= 10) {
        this.lastSentTime = currentTime;
        this.saveProgress(currentTime);
      }
    })
  }

  onPause() {
    const currentTime = this.videoElement.nativeElement.currentTime;
    this.saveProgress(currentTime);
  }

  onTimeUpdate() {
    // You can use for interval-based save
  }

  saveProgress(time: number) {
    const payload = {
      courseId: this.courseId,
      currentTime: time
    }
    this.service.saveProgress(payload).subscribe();
  }

  ngOnDestroy(): void {
    clearInterval(this.intervalRef);
  }
}
