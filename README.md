# BlogApp Backend

A robust and scalable RESTful API backend for the BlogApp, built with Spring Boot 3.5.6. This application provides comprehensive blog management functionality including user authentication, post management, media uploads, and reaction systems.

## 🚀 Features

### Authentication & Security
- **JWT-based Authentication** - Secure token-based authentication system
- **Password Encryption** - BCrypt password hashing for secure storage
- **CORS Configuration** - Cross-origin resource sharing support
- **Spring Security** - Comprehensive security configuration with role-based access

### User Management
- User registration with validation
- Secure login with JWT token generation
- Profile update (email/password)
- Unique username and email constraints

### Post Management
- Create, read, update, and delete blog posts
- Multi-media support (IMAGE, AUDIO, VIDEO, TEXT)
- Search functionality (by title and content)
- Like and dislike system with reaction tracking
- Post metadata (like count, dislike count, timestamps)

### Media Handling
- Multi-file upload support
- Support for images, audio, and video files
- File size limit: 20MB per request
- Organized storage in uploads directory
- Content type detection and delivery

### Reactions System
- Like/dislike functionality for posts
- One reaction per user per post (unique constraint)
- Real-time reaction counts
- Remove reaction capability

## 📋 Technical Stack

### Core Technologies
- **Java 17** - Programming language
- **Spring Boot 3.5.6** - Application framework
- **Maven** - Dependency management and build tool

### Frameworks & Libraries
- **Spring Data JPA** - Database operations and ORM
- **Spring Security** - Authentication and authorization
- **Spring Validation** - Input validation
- **Spring Web** - RESTful web services

### Database
- **MySQL** - Primary database (configurable)
- **PostgreSQL** - Alternative database support
- **Hibernate** - ORM implementation

### Security & Authentication
- **JWT (JSON Web Tokens)** - Token-based authentication
  - `jjwt-api` 0.11.5
  - `jjwt-impl` 0.11.5
  - `jjwt-jackson` 0.11.5
- **BCrypt** - Password encryption

### Development Tools
- **Lombok** - Reduce boilerplate code
- **ModelMapper 3.2.0** - Object mapping
- **Spring Boot DevTools** - Development utilities

## 🏗️ Project Structure

```
BlogAppBackend/
├── src/
│   ├── main/
│   │   ├── java/com/blogapp/blogappbackend/
│   │   │   ├── BlogAppBackendApplication.java
│   │   │   ├── controllers/
│   │   │   │   ├── HealthController.java
│   │   │   │   ├── MediaController.java
│   │   │   │   ├── PostController.java
│   │   │   │   ├── PostReactionController.java
│   │   │   │   └── UserController.java
│   │   │   ├── entity/
│   │   │   │   ├── Post.java
│   │   │   │   ├── PostReaction.java
│   │   │   │   └── User.java
│   │   │   ├── repository/
│   │   │   │   ├── PostReactionRepository.java
│   │   │   │   ├── PostRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── security/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── service/
│   │   │   │   ├── MediaService.java
│   │   │   │   ├── PostReactionService.java
│   │   │   │   ├── PostReactionServiceImplementation.java
│   │   │   │   ├── PostService.java
│   │   │   │   ├── PostServiceImplementation.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── UserServiceImplementation.java
│   │   │   └── utilities/
│   │   │       ├── JwtFilter.java
│   │   │       └── JwtService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/blogapp/blogappbackend/
│           └── BlogAppBackendApplicationTests.java
├── uploads/
│   ├── images/
│   └── others/
├── Dockerfile
├── pom.xml
└── README.md
```

## 🔧 Configuration

### Environment Variables

The application uses environment variables for configuration. You can set these in your system or use the defaults:

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_URL` | Database connection URL | `jdbc:mysql://localhost:3306/blog_app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USER` | Database username | `root` |
| `DB_PASS` | Database password | `Admin` |
| `DB_DDL` | Hibernate DDL auto mode | `update` |
| `SHOW_SQL` | Show SQL queries in logs | `true` |
| `APP_JWT_SECRET` | JWT signing secret key | (provided in application.properties) |
| `JWT_EXPIRATION` | JWT token expiration time (ms) | `86400000` (24 hours) |
| `APP_UPLOAD_DIR` | Media upload directory | `uploads` |

### application.properties

```properties
spring.application.name=BlogAppBackend

# Database Configuration
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/blog_app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC}
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASS:Admin}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=${DB_DDL:update}
spring.jpa.show-sql=${SHOW_SQL:true}

# JWT Configuration
app.jwtSecret=${APP_JWT_SECRET:your-secret-key}
app.jwtExpirationMs=${JWT_EXPIRATION:86400000}

# Media Upload
app.uploadDir=${APP_UPLOAD_DIR:uploads}
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL or PostgreSQL database
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

### Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE blog_app;
```

2. The application will automatically create tables on first run using Hibernate DDL auto.

### Installation & Running

1. **Clone the repository**
```bash
git clone https://github.com/akshalder11/blogAppBackend.git
cd BlogAppBackend
```

2. **Configure environment variables** (Optional)
   - Update `application.properties` or set environment variables

3. **Build the project**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/BlogAppBackend-v1.0.0.jar
```

The application will start on `http://localhost:8080`

### Docker Deployment

Build and run using Docker:

```bash
# Build Docker image
docker build -t blogapp-backend .

# Run container
docker run -p 8080:8080 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/blog_app \
  -e DB_USER=root \
  -e DB_PASS=yourpassword \
  blogapp-backend
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication Endpoints

#### Register User
- **POST** `/users/registerUser`
- **Body:**
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```
- **Response:** User object with hashed password

#### Login User
- **POST** `/users/loginUser`
- **Body:**
```json
{
  "username": "johndoe",
  "password": "securePassword123"
}
```
- **Response:**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Update User
- **PUT** `/users/updateUser` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "userId": "1",
  "email": "newemail@example.com",
  "password": "newPassword123"
}
```

### Post Endpoints

#### Create Post
- **POST** `/posts/createPost` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "user": { "id": 1 },
  "title": "My First Blog Post",
  "content": "This is the content of my blog post...",
  "mediaType": "IMAGE",
  "mediaUrls": ["http://localhost:8080/api/media/uploads/images/file1.jpg"]
}
```

#### Get All Posts
- **GET** `/posts/allPost`
- **Response:** Array of post objects

#### Get Post by ID
- **POST** `/posts/getPost` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "postId": "1",
  "userId": "1"
}
```

#### Update Post
- **PUT** `/posts/updatePost` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "postId": "1",
  "title": "Updated Title",
  "content": "Updated content...",
  "mediaType": "TEXT",
  "mediaUrls": []
}
```

#### Delete Post
- **DELETE** `/posts/deletePost` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "postId": "1"
}
```

#### Search Posts by Title
- **GET** `/posts/search/title` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "title": "blog"
}
```

#### Search Posts by Content
- **GET** `/posts/search/content` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "content": "java"
}
```

### Media Endpoints

#### Upload Media
- **POST** `/media/upload` 🔒
- **Headers:** 
  - `Authorization: Bearer <token>`
  - `Content-Type: multipart/form-data`
- **Body:** Form-data with key `file` (supports multiple files)
- **Response:**
```json
{
  "mediaUrls": [
    "http://localhost:8080/api/media/uploads/images/uuid-filename.jpg"
  ]
}
```

#### Get Media File
- **GET** `/media/uploads/{type}/{filename}`
- **Example:** `/media/uploads/images/photo.jpg`
- **Response:** Binary file data with appropriate content type

### Reaction Endpoints

#### React to Post
- **POST** `/postReactions/reactPost` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "postId": "1",
  "userId": "1",
  "reactionType": "LIKE"
}
```
- **Reaction Types:** `LIKE`, `DISLIKE`

#### Remove Reaction
- **DELETE** `/postReactions/removeReactPost` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "postId": "1",
  "userId": "1"
}
```

#### Get All Reactions for Post
- **GET** `/postReactions/getAllReactForPost` 🔒
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{
  "postId": "1"
}
```
- **Response:**
```json
{
  "postId": 1,
  "reactionCount": 5,
  "reactions": [...]
}
```

### Health Check

#### Check API Status
- **GET** `/health`
- **Response:**
```json
{
  "status": "Backend is up and running"
}
```

🔒 = Requires JWT Authentication

## 🔐 Security Features

### CORS Configuration
The application allows requests from:
- `http://localhost:5173` (Development)
- `http://192.168.1.10:5173` (Local network)
- `https://akshalder11-blogapp.netlify.app` (Production)

### Public Endpoints
The following endpoints are accessible without authentication:
- `/api/health`
- `/api/users/loginUser`
- `/api/users/registerUser`
- `/api/posts/allPost`
- `/api/media/uploads/**`

All other endpoints require JWT authentication.

### JWT Token
- Token expiration: 24 hours (configurable)
- Include in requests: `Authorization: Bearer <your-token>`
- Token contains: username and userId claims

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_pic VARCHAR(255),
    created_at TIMESTAMP NOT NULL
);
```

### Posts Table
```sql
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT NOT NULL,
    media_type VARCHAR(20) NOT NULL,
    like_count INT NOT NULL DEFAULT 0,
    dislike_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Post Reactions Table
```sql
CREATE TABLE post_reactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    reaction_type VARCHAR(20) NOT NULL,
    UNIQUE KEY unique_user_post (user_id, post_id)
);
```

### Post Media URLs Table
```sql
CREATE TABLE post_media_urls (
    post_id BIGINT NOT NULL,
    media_url VARCHAR(500),
    FOREIGN KEY (post_id) REFERENCES posts(id)
);
```


## 📦 Build for Production

Create a production-ready JAR:

```bash
mvn clean package -DskipTests
```

The JAR file will be created in `target/BlogAppBackend-v1.0.0.jar`

## 🛠️ Development Tools

- **Lombok** - Reduces boilerplate with annotations
  - `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`
- **Spring DevTools** - Hot reload during development
- **Maven Wrapper** - Included (`mvnw` and `mvnw.cmd`)

## 📝 Entity Models

### User
- id (Long)
- username (String, unique)
- email (String, unique)
- password (String, encrypted)
- profilePic (String)
- createdAt (LocalDateTime)

### Post
- id (Long)
- user (User, ManyToOne)
- title (String)
- content (String, LOB)
- mediaType (Enum: IMAGE, AUDIO, VIDEO, TEXT)
- mediaUrls (List<String>)
- likeCount (int)
- dislikeCount (int)
- hasLikedByCurrentUser (Boolean, transient)
- hasDisLikedByCurrentUser (Boolean, transient)
- createdAt (LocalDateTime)
- updatedAt (LocalDateTime)

### PostReaction
- id (Long)
- userId (Long)
- postId (Long)
- reactionType (Enum: LIKE, DISLIKE)
- Unique constraint: (userId, postId)

## 🤖 AI-Powered Development

This project leveraged cutting-edge AI tools to accelerate development and improve code quality:

### ChatGPT Integration
- **Architecture Design** - Assisted in designing RESTful API structure and best practices
- **Problem Solving** - Helped troubleshoot complex bugs and optimization issues
- **Documentation** - Generated comprehensive API documentation and code comments
- **Code Review** - Provided suggestions for code improvements and security enhancements

### GitHub Copilot
- **Boilerplate Code Reduction** - Significantly reduced time writing repetitive code patterns
- **Smart Auto-completion** - Intelligent code suggestions for service implementations and controllers

### Impact on Development
- ⚡ **50% faster development** - Reduced time spent on repetitive tasks
- 🎯 **Improved code quality** - AI-suggested best practices and patterns
- 📚 **Better documentation** - Comprehensive and consistent documentation
- 🐛 **Faster debugging** - Quick identification and resolution of issues
- 🔄 **Consistent patterns** - Uniform code structure throughout the project

> **Note:** While AI tools were instrumental in accelerating development, all code was reviewed, tested, and validated to ensure quality, security, and adherence to best practices.

## 🔄 Future Enhancements

### Planned Features
- [ ] Comment system for blog posts
- [ ] User profile customization with avatar upload
- [ ] Post categories and tags
- [ ] Advanced search with filters (date, author, category)
- [ ] Email notifications for post interactions
- [ ] OAuth2 integration (Google, GitHub login)
- [ ] Rate limiting and API throttling
- [ ] WebSocket support for real-time notifications
- [ ] Post scheduling and draft functionality
- [ ] User follow/unfollow system
- [ ] Trending posts algorithm
- [ ] Content moderation system
- [ ] Analytics dashboard for post performance

### Technical Improvements
- [ ] Redis caching for improved performance
- [ ] Elasticsearch integration for advanced search
- [ ] GraphQL API endpoint
- [ ] Comprehensive API documentation with Swagger/OpenAPI
- [ ] Monitoring with Prometheus and Grafana
- [ ] CI/CD pipeline with GitHub Actions
- [ ] Automated testing with increased coverage
- [ ] Kubernetes deployment configuration
- [ ] Database migration with Flyway/Liquibase
- [ ] API versioning strategy


### Monitoring & Metrics
- Spring Boot Actuator endpoints available for production monitoring
- SQL query logging enabled for development (configurable)
- Exception handling with detailed error messages

## 🔒 Security Best Practices

### Implemented Security Measures
- ✅ Password hashing with BCrypt (cost factor: 10)
- ✅ JWT token-based authentication
- ✅ CORS configuration for controlled access
- ✅ Input validation with Spring Validation
- ✅ Environment variable configuration for secrets
- ✅ Stateless session management

### Security Recommendations for Production
- 🔐 Use HTTPS/TLS for all communications
- 🔑 Rotate JWT secret keys regularly
- 🛡️ Implement rate limiting to prevent brute force attacks
- 📝 Add comprehensive logging and audit trails
- 🚫 Implement OWASP security headers
- 🔍 Regular security audits and dependency updates
- 💾 Database backup and recovery strategy
- 🔐 Implement API key management for third-party integrations

## 🐛 Known Issues & Troubleshooting

### Common Issues

**Issue: Database Connection Failed**
```
Solution: Verify MySQL is running and credentials in application.properties are correct
```

**Issue: JWT Token Expired**
```
Solution: Token expires after 24 hours. Login again to get a new token
```

**Issue: File Upload Fails**
```
Solution: Check file size (max 20MB) and ensure uploads directory exists with write permissions
```

**Issue: CORS Error from Frontend**
```
Solution: Add your frontend URL to the allowed origins in SecurityConfig.java
```

## 👤 Author

**Akash Halder**
- GitHub: [@akshalder11](https://github.com/akshalder11)
- Frontend: [BlogApp Frontend](https://akshalder11-blogapp.netlify.app)

