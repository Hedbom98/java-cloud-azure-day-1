# All not CRUD-operations where used in the frontend of this project.


# Java Cloud Azure Day One

## Learning Objectives

- Understand how to deploy a front end to an Azure Blob
- Understand how to deploy an API to an Azure App
- Understand how to spin up an Azure Postgres instance

## Instructions

1. Fork this repository
2. Clone your fork to your machine
   
Optional Extras (we'll look at these next week)

3. Install the [Microsoft Azure Storage Explorer](https://azure.microsoft.com/en-us/products/storage/storage-explorer/)
4. Run the [Microsoft Azurite Storage Emulator](https://learn.microsoft.com/en-us/azure/storage/common/storage-use-azurite?tabs=visual-studio%2Cblob-storage)

For this exercise you should get a full stack web application working ready to deploy to Azure next week

## Core Activity
 
~~You are going to deploy a simple API to Azure.~~  Create a library of something, this could be books, music, video games, tools, spoons or something else more obscure. You want to have multiple endpoints that are to linked informtion (similar to Books, Authors and Publishers).

- Back end API using Java and the Spring Boot Application starter package
- Database using PostgreSQL hosted on ~~Azure~~ Neon (we can point it at an Azure one later)

## Extension Activity

- Create a front end using either `React` or `Angular` which calls the API endpoints.  Your frontend repository should ideally be pushed [here](https://github.com/boolean-uk/java-cloud-azure-day-1-frontend) ~~and deployed to Azure~~.
- Add any extended functionality you can think of to your API.

The notes below are not needed until we start looking at Azure in depth.

## Notes

A simpler deployment stategy using a JAR file is found here: [https://learn.microsoft.com/en-us/azure/spring-apps/basic-standard/how-to-launch-from-source](https://learn.microsoft.com/en-us/azure/spring-apps/basic-standard/how-to-launch-from-source) you will need to install the Azure CLI tool to make this happen.

Step by step instructions for deploying a Spring App and Postgres database can be found here: [https://learn.microsoft.com/en-us/azure/spring-apps/enterprise/quickstart-deploy-restful-api-app?tabs=Azure-portal-maven-plugin-ent%2CAzure-portal-maven-plugin&pivots=sc-consumption-plan](https://learn.microsoft.com/en-us/azure/spring-apps/enterprise/quickstart-deploy-restful-api-app?pivots=sc-enterprise&tabs=Azure-portal-maven-plugin-ent%2CAzure-portal-maven-plugin) This makes extensive use of Maven so you may need to investigate using that as part of the process.

Investigate each strategy using a very basic Spring App initially and then choose one to deploy your application.

We will be only using free tiers to create Azure items.

- Use the [Microsoft Azurite Storage Emulator](https://learn.microsoft.com/en-us/azure/storage/common/storage-use-azurite?tabs=visual-studio%2Cblob-storage)
- Use the [Microsoft Azure Storage Explorer](https://azure.microsoft.com/en-us/products/storage/storage-explorer/)
- FYI this is a legacy product: [Microsoft Azure Storage Emulator](https://learn.microsoft.com/en-us/azure/storage/common/storage-use-emulator)
