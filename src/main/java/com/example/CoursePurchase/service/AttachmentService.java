package com.example.CoursePurchase.service;

import com.example.CoursePurchase.models.Attachment;
import com.example.CoursePurchase.models.AttachmentDTO;

public interface AttachmentService {

    public Attachment uploadAttachment(Attachment attachment, byte[] fileDetails);

    public AttachmentDTO fetchAttachmentDetails(Long attachmentId);

    public String generatePreSignedUrl(Long attachmentId);
}
