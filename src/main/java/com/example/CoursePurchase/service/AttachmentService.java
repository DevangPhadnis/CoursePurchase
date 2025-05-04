package com.example.CoursePurchase.service;

import com.example.CoursePurchase.models.Attachment;
import com.example.CoursePurchase.models.AttachmentDTO;

public interface AttachmentService {

    public Long uploadAttachment(Attachment attachment, byte[] fileDetails);

    public AttachmentDTO fetchAttachmentDetails(Long attachmentId);
}
