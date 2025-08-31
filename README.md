# 📚 Course Purchase Application

A full-stack **Course Purchase Application** built with **Angular**, **Spring Boot**, and **MySQL**.
This project is designed as a **hands-on learning exercise** to explore full-stack development and deployment on **AWS**.

---

## 🚀 Features

* User-friendly course browsing interface
* Secure authentication and authorization with JWT
* Course purchase and enrollment functionality with **Razorpay payment integration**
* Video playback with resume support (via AWS S3 presigned URLs)
* Email and Whatsapp notification services (SMTP + Twilio)
* OAuth2 login (Google Sign-In support)
* Backend REST APIs built with Spring Boot
* Database integration with MySQL
* Deployment ready for AWS services (EC2, RDS, S3)

---

## 🛠️ Tech Stack

* **Frontend:** Angular (CLI v14)
* **Backend:** Java Spring Boot v3.4.4
* **Database:** MySQL
* **Deployment:** AWS EC2, AWS RDS, AWS S3
* **Integrations:** Razorpay (Payments), Twilio (Whatapp Receipt), SMTP (Email), OAuth2 (Google Login), AWS S3 (Video Streaming)
* **Runtime:** Node.js v18, Java v17.0.7

---

## ⚙️ Getting Started

Follow the steps below to set up the project locally:

### 1. Clone the Repository

```bash
git clone https://github.com/DevangPhadnis/CoursePurchase.git
cd CoursePurchase
```

### 2. Backend Setup (Spring Boot)

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Create a MySQL database:

   ```sql
   CREATE DATABASE coursemanagement;
   ```

3. Update your `application.properties` with the following values:

   ```properties
   # MySQL Configuration
   spring.datasource.name = coursemanagement
   spring.datasource.url = jdbc:mysql://localhost:3306/coursemanagement
   spring.datasource.username = root
   spring.datasource.password = root
   spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver

   # Hibernate Config
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

   # JWT Config
   jwt.TokenExpiry = 30
   jwt.SecretKey = TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V

   # AWS Config (Used for file and video storage)
   aws.accessKey = <your_aws_access_key>
   aws.secretKey = <your_aws_secret_key>
   aws.bucket.name = <your_bucket_name>

   # Twilio Config
   twilio.account.sid = <your_twilio_sid>
   twilio.auth.token = <your_twilio_auth_token>
   twilio.phone.number = <your_twilio_phone_number>

   # Email Config
   spring.mail.host = <your_smtp_host>
   spring.mail.port = <your_smtp_port>
   spring.mail.username = <your_email>
   spring.mail.password = <your_email_password>
   spring.mail.properties.mail.smtp.auth = true
   spring.mail.properties.mail.smtp.starttls.enable = true

   # OAuth Config
   oauth.clientId = <your_google_oauth_client_id>

   # Razorpay Config
   razorpay.keyId = <your_razorpay_key_id>
   razorpay.secretKey = <your_razorpay_secret_key>
   ```

4. Build and run the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

   The backend server will start on `http://localhost:8080`.

---

### 3. Frontend Setup (Angular)

1. Navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Update the **environment configuration** in Angular for:

   * **Razorpay Key** (for payment integration)
   * **Google OAuth Client ID** (for login with Google)
   * **API Base URL** (for backend integration)

   Example (`environment.ts`):

   ```ts
   export const environment = {
     production: false,
     apiBaseUrl: 'http://localhost:8080',
     razorpayKey: '<your_razorpay_key_id>',
     googleClientId: '<your_google_oauth_client_id>'
   };
   ```

4. Start the Angular development server:

   ```bash
   ng serve
   ```

   The frontend will be available at `http://localhost:4200`.

---

### 4. Environment Setup

Make sure you have the following installed/configured:

* **Java 17.0.7**
* **Spring Boot 3.4.4**
* **Angular CLI v14**
* **Node.js v18**
* **MySQL** running locally
* **AWS credentials** (for S3 + RDS deployment, used for video streaming and file storage)
* **Twilio account** (for Whatsapp service)
* **SMTP email service** configured
* **Google OAuth credentials** (for login with Google)
* **Razorpay API keys** (for payment integration)

---

## 🎥 Demo / Screenshots

* **Login Page**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/2e4358df-4473-44d5-ad70-4fb2f1d5bd3c" />


* **Course Listing**
   <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/aa609973-f01d-4e41-bf04-4c2fa86d4733" />


* **Purchase Flow**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/c45f17f0-7ccd-485c-b84f-87d6546ae87d" />

  
* **Purchased Courses**
  <img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/534b45e4-f3aa-45ef-9403-83ce404f3762" />
  
---

📩 Contact

For any queries, feel free to reach out
- Email: **devangphadnis2001@gmail.com**
- GitHub Profile: [**Devang Phadnis**](https://github.com/DevangPhadnis)
- GitHub Profile: [**Course Purchase Platform**](http://ec2-3-82-98-7.compute-1.amazonaws.com/login)
---
