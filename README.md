# About the project

ToDoApp is a simple Java Spring application, built to store notes in a database. You can perform CRUD operations on the notes.

# Used technologies

The notes are stored in a MySQL database.There are unit and integration tests, that are running on the H2 database. The app also uses Jacoco reports for comprehensive test coverage.

# Generating Jacoco reports

* Execute the following command:
> mvn jacoco:report

# CI/CD

In the Git repository, three pipelines are established. The Continuous Integration (CI) pipeline executes a Maven build upon each branch and commit. Another pipeline focuses on building a Docker image for every branch, ensuring compatibility with changes made outside the main branch. The final pipeline operates exclusively on the main branch, where changes from other branches are merged. Upon merging, an image is built and pushed to the Docker Hub repository automatically, streamlining the deployment process.
