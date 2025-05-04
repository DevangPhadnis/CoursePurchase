import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/login/service/authentication.service';
import { UserService } from 'src/user/service/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  constructor(private userService: UserService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.authenticationService.getProfileDetails().subscribe((res: any) => {
      this.setFormValue(res.data);
      this.user?.controls?.username?.disable();
    })
  }

  userDetailsId: number = -1;
  genderList = [
    {
      genderId: "Male",
      genderName: "Male",
    },
    {
      genderId: "Female",
      genderName: "Female",
    },
    {
      genderId: "Not to Disclose",
      genderName: "Not to Disclose",
    },
  ];
  createdDate: any;

  user = new FormGroup({
    username: new FormControl('', [Validators.required]),
    userFullName: new FormControl('', [Validators.required]),
    userEmailId: new FormControl({value: null, disabled: true}),
    gender: new FormControl('', [Validators.required]),
    userDateOfBirth: new FormControl('', [Validators.required]),
    userMobileNumber: new FormControl('', [Validators.required, Validators.pattern('^[6-9][0-9]{9}$')]),
    userAddressOne: new FormControl('', [Validators.required]),
    userAddressTwo: new FormControl('', [Validators.required]),
    userAddressThree: new FormControl(''),
  })

  setFormValue(restemp: any): void {
    this.user?.controls?.username?.setValue(restemp?.userName);
    this.user?.controls?.userFullName?.setValue(restemp?.fullName);
    this.user?.controls?.userEmailId?.setValue(restemp?.email);
    this.user?.controls?.gender?.setValue(restemp?.gender);
    this.user?.controls?.userDateOfBirth?.setValue(restemp?.dateOfBirth);
    this.user?.controls?.userMobileNumber?.setValue(restemp?.mobileNumber);
    this.user?.controls?.userAddressOne?.setValue(restemp?.addressOne);
    this.user?.controls?.userAddressTwo?.setValue(restemp?.addressTwo);
    this.user?.controls?.userAddressThree?.setValue(restemp?.addressThree);
    this.userDetailsId = restemp?.userDetailsId;
    this.createdDate = new Date(restemp?.userDetailsCreatedDate);
  }

  onSubmit(): void {
    const payload = {
      userName: this.user?.controls?.username?.value,
      fullName: this.user?.controls?.userFullName?.value,
      email: this.user?.controls?.userEmailId?.value,
      gender: this.user?.controls?.gender?.value,
      dateOfBirth: this.user?.controls?.userDateOfBirth?.value,
      mobileNumber: this.user?.controls?.userMobileNumber?.value,
      addressOne: this.user?.controls?.userAddressOne?.value,
      addressTwo: this.user?.controls?.userAddressTwo?.value,
      addressThree: this.user?.controls?.userAddressThree?.value,
      createdDate: this.createdDate,
      usrDetailsId: this.userDetailsId
    }
    this.userService.saveUserDetails(payload).subscribe((res: any) => {
      this.authenticationService.getProfileDetails().subscribe((restemp: any) => {
        this.setFormValue(restemp.data);
      })
    }, (err) => {
      console.log("err", err);
    })
  }
}
