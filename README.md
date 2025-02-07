# Symptom Checker Microservice Exercise

## Description

Your task is to create a backend microservice that simulates a simple symptom checker. The service will:

1. Collect basic user information.
2. Ask questions about symptoms based on previously reported symptoms.
3. Provide a probable condition based on the reported symptoms.

The assessment should end after **3 rounds of questions** or no other symptoms can be asked about.

---

## Requirements

### **Microservice Design**

Build a microservice using **Spring Boot**, or any other Java-based backend framework of your choice. Use [**DynamoDB
**](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html#docker) as
your database for this exercise. The microservice must implement the following **RESTful endpoints**:

#### **Authentication**

- **POST /auth/register**
    - Registers a new user.
    - Input: A JSON body containing the following fields:
        - `email` (string): Email of the user (this is also the username).
        - `password` (string): Password of the user.
        - `age` (integer): Age of the user.
        - `gender` (string): Gender of the user.
    - Response: A success message upon successful registration.

- **POST /auth/login**
    - Logs in a registered user.
    - Input: A JSON body containing the following fields:
        - `email` (string): Email of the user (this is also the username).
        - `password` (string): Password of the user.
    - Response:
        - `user_id` (string): The user initiating the assessment.

#### **Start Assessment**

- **POST /assessment/start**
    - Starts a new assessment for a user.
    - Input: A JSON body containing the following fields:
        - `user_id` (string): The user initiating the assessment.
        - `initial_symptoms` (list of strings): The initial symptoms reported by the user.
    - Response:
        - `assessment_id` (string): A unique identifier for the assessment.
        - `next_question_id` (string): A symptom to be asked.

#### **Answer Question**

- **POST /assessment/{assessment_id}/answer**
    - Submits an answer to a symptom question during the assessment.
    - Input:
        - `assessment_id` (path parameter): The unique ID of the assessment.
        - A JSON body containing:
            - `question_id` (string): The symptom being answered.
            - `response` (string): The user's answer ("yes" / "no").
    - Response:
        - `next_question_id` (string): A symptom to be asked.
        - Returns null if no further questions are required.

#### **Get Assessment Result**

- **GET /assessment/{assessment_id}/result**
    - Retrieves the final condition diagnosis for a completed assessment.
    - Input:
        - `assessment_id` (path parameter): The unique ID of the assessment.
    - Response:
        - `condition` (string): The most probable condition.
        - `probabilities` (dictionary): Probabilities for all conditions.

---

### **Question Generation**

- Use the **data provided** to guide the questions asked during the assessment.
- Ensure the service:
    - Avoids repeating questions already asked.
    - Bonus: Dynamically selects the next most relevant question based on the reported symptoms.

---

### **Assessment Logic**

- Calculate the probability of each condition based on the reported symptoms using [**Bayes' Theorem
  **](https://en.wikipedia.org/wiki/Bayes%27_theorem).
- After **3 rounds of questions**, or no other symptoms can be asked about, the service should:
    - End the assessment.
    - Provide the most probable condition.
    - Provide a list of all possible conditions and probabilities

---

### **Deployment**

- Containerize the microservice using **Docker**.
- Ensure the service runs on `http://0.0.0.0:8080`.

---

### **CI/CD Integration**

- Implement a **GitHub Action** to automatically build, test, and validate the microservice:
    - **Build and Test**: Ensure the service compiles and all tests pass.
    - **Static Analysis**: Include linting and formatting checks.
    - **Docker Build Validation**: Validate that the Docker container can be built successfully.

---

## Evaluation Criteria

Your submission will be evaluated based on the following:

1. **Code Quality**: Clean, maintainable code that adheres to best practices.
2. **Correctness**: Proper use of the provided data tables for symptom probability and condition prevalence.
3. **Logic Implementation**: Accurate implementation of Bayes' Theorem and assessment logic.
4. **Dockerization**: Working Docker container and validation of deployment.
5. **Testing**: Tests that validate key functionality.
6. **CI/CD Workflow**: A GitHub Action workflow to ensure automated validation of the solution.

This exercise is designed to evaluate your ability to build scalable, logical microservices with a real-world healthcare
application in mind.

## High level solution

User Input:
Initial symptoms → ["Sneezing", "Runny nose"] specific case :

1. Compute Probabilities for "Sneezing"
2. Compute Probabilities for "Runny Nose" (Using the updated probabilities from "Sneezing")
3. When selecting the Most Relevant Next Symptom
   YES: P(S∣C)
   NO: P(¬S∣C)=1−P(S∣C) - complement
4. Compute the Variance of Each Symptom
5. Take the highest variance as it means the symptom is more informative
6. Compute Probabilities for next selected symptom (Using the updated probabilities from "Runny Nose") and so on

To start the project: docker-compose up -d

There are 2 containers:
http://localhost:8000 - DynamoDB running locally
http://localhost:8001/ - A simple nice UI to visualize the data
     
