# Hospital MVC Application

## Overview
This is a Spring Boot application designed for managing hospital patient records. It features user authentication and authorization using Spring Security, and provides functionalities to add, edit, delete, and view patient records.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Endpoints](#endpoints)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

## Features
- User and admin roles with different permissions
- CRUD operations for patient records
- Pagination and search functionality
- Custom login page
- Access control with Spring Security

## Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/hospital-mvc.git
    ```
2. Navigate to the project directory:
    ```sh
    cd hospital-mvc
    ```
3. Build the project using Maven:
    ```sh
    mvn clean install
    ```
4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage
- Access the application at `http://localhost:8080`
- Login with the default users:
    - Username: `user1`, Password: `123` (Role: USER)
    - Username: `admin`, Password: `123` (Role: USER, ADMIN)

## Project Structure
![image](https://github.com/bajadda1/hospital-spring-MVC/assets/143662918/4dedcab0-796a-4277-9fb3-ca0a1caca01e)

## Endpoints
- `GET /user/index`: List all patients
- `GET /`: Redirect to patient list
- `GET /user/index-page`: List patients with pagination and search
- `GET /admin/delete`: Delete a patient (Admin only)
- `GET /admin/add-patient`: Form to add a new patient (Admin only)
- `POST /admin/save`: Save a new or edited patient (Admin only)
- `GET /admin/edit`: Form to edit an existing patient (Admin only)
- `GET /login`: Login page
- `GET /accessDenied`: Access denied page

## Security
- Uses Spring Security for authentication and authorization
- Method-level security with `@PreAuthorize`
- Custom login page
- Session-based user details management
- Password encryption with `PasswordEncoder`

## Contributing
1. Fork the repository
2. Create a new branch:
    ```sh
    git checkout -b feature-branch
    ```
3. Make your changes
4. Commit your changes:
    ```sh
    git commit -m 'Add some feature'
    ```
5. Push to the branch:
    ```sh
    git push origin feature-branch
    ```
6. Open a pull request

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
