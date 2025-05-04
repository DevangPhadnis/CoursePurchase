package com.example.CoursePurchase.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.CoursePurchase.dao.AttachmentRepository;
import com.example.CoursePurchase.models.Attachment;
import com.example.CoursePurchase.models.AttachmentDTO;
import com.example.CoursePurchase.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Override
    public Long uploadAttachment(Attachment attachment, byte[] fileDetails) {
        try {
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + System.currentTimeMillis() + attachment.getFileExtension();
            attachment.setFileName(fileName);
            attachmentRepository.save(attachment);
            Long attachmentId = attachment.getAttachmentId();
            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(Long.parseLong(attachment.getContentSize()));
            metaData.setContentType(attachment.getContentType());
            this.amazonS3.putObject(new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(fileDetails), metaData));

            return attachmentId;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public AttachmentDTO fetchAttachmentDetails(Long attachmentId) {
        try {
            if(attachmentId != null) {
                Optional<Attachment> attachment = attachmentRepository.findById(attachmentId);
                if(attachment.isPresent()) {
                    String fileName = attachment.get().getFileName();
                    S3Object s3Object = amazonS3.getObject(bucketName, fileName);
                    S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

                    byte[] fileContent = IOUtils.toByteArray(s3ObjectInputStream);

                    AttachmentDTO attachmentDTO = new AttachmentDTO();
                    attachmentDTO.setOriginalFileName(attachment.get().getOriginalFileName());
                    attachmentDTO.setContentSize(attachment.get().getContentSize());
                    attachmentDTO.setContentType(attachment.get().getContentType());
                    attachmentDTO.setFileExtension(attachment.get().getFileExtension());
                    attachmentDTO.setCreatedDate(attachment.get().getCreatedDate());
                    attachmentDTO.setFileBytes(fileContent);

                    return attachmentDTO;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }
}
