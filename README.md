# Project Management

## Overview
Project Management is an open-source application that a versatile application utilized by both companies and employees, project management used to manage company projects, project participants, progress, and project evaluations. The tool is built using .NET and Angular. The Management Project is regularly managed and updated by the staff team of NCC.
 
## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
  - [Building](#building)
  - [Running](#running)
- [Video Tutorial](#video-tutorial)
- [License](#license)

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- [Visual Studio 2022](https://visualstudio.microsoft.com/) installed.
- [.NET Core 3.1.426 SDK](https://dotnet.microsoft.com/en-us/download/dotnet/3.1) installed.
- [ASP.NET Runtime 3.1](https://dotnet.microsoft.com/en-us/download/dotnet/3.1) installed.
- [Visual Studio Code](https://code.visualstudio.com/) installed.
- [Node.js 14.20](https://nodejs.org/en/blog/release/v14.20.0) and npm (Node.js Package Manager) installed.
- [SQL Server](https://www.microsoft.com/en-in/sql-server/sql-server-downloads) installed.
- <List any other prerequisites>

### Backend Setup

1. **Create a folder** to store the backend and frontend code.
- example:  folder `ncc-project`

2. **Open a command prompt** in the created folder.

3. **Clone the backend repository** using the following command:

   ```bash
   git clone https://github.com/ncc-erp/ncc-erp-project.git
   
4. Open the backend solution using **Visual Studio 2022**:

- Launch `Visual Studio 2022`.
- Select `File` > `Open` > `Project/Solution.`
- Navigate to the backend folder within created folder `timesheet` and open the solution file.
5. Restore NuGet packages:

- In Solution Explorer, right-click the solution and select **Restore NuGet Packages**.
6. **Set the startup project:**

- Right-click the desired project (usually the API project) in `Solution Explorer`.
Select **Set as StartUp Project**.

7. Get the local database file and rename it:

- Locate the **local-project.sql** file in your local environment.
- Rename it to **local-project.sql**
8. Update the `appsettings.json` file:

- Open the `appsettings.json` file in the backend project.

- Locate the `ConnectionStrings` section.

- Update the **Default** connection string to match your local database information:



```json
{
  "ConnectionStrings": {
    "Default": "Server=servername; Database=local-project; User ID=yourUserId;Password=yourPassword;"
  },
  // ... other settings ...
}
```

9. Press `F5` or select `Debug` > `Start Debugging` to run the backend.
    
## Deploying Backend to IIS

### Prerequisites

Before you begin deploying the backend to IIS, ensure you have met the following prerequisites:

- Have completed the steps to configure and deploy the backend project as outlined in your provided instructions.

### Steps

1. **Build the Application**: First, you need to build your backend application. Make sure you have done this using Visual Studio or through the command line.

2. **Publish the Application**: Next, you need to publish your backend project to generate the necessary files for deployment on IIS. This can be done using the following command in your backend project:

   ```bash
   dotnet publish -c Release

This command will generate the necessary files in the bin/Release/netcoreapp3.1/publish directory.

1. Configure IIS: Open the "Turn Windows features on or off" window on your computer and ensure that "Internet Information Services" and "ASP.NET" are turned on.

2. Add Application to IIS: Open the IIS Manager and follow these steps:

- Right-click on "Sites" and select "Add Website."
- Name your website, specify the path to the published directory, and configure options like IP Address and Port.
- In the "Application pool" section, you can choose an existing application pool or create a new one.
3. Configure Database Connection: Make sure the connection string in the appsettings.json file is updated to match the database you are deploying to IIS.

4. Start the Application: In the IIS Manager, select the application you just added and right-click to start the application.

5. Test the Application: Open a web browser and navigate to the URL of the website you configured in IIS. Ensure that the application works as expected.

Remember to consult the IIS documentation and specific guides for the version of IIS you are using for detailed information on configuring and deploying .NET applications on IIS.

Feel free to reach out to the NCC staff team if you encounter any issues during the deployment process.

### Frontend Setup
1. Open Front-end repository
```bash
cd C:\Users\dell\Documents\CSharpDotnet\timesheet\ncc-erp-project\angular
```
- run code
```bash
code .
```

2. Install Angular CLI 9.1.15 globally:

```bash

npm install -g @angular/cli@9.1.15

```
3. Install frontend dependencies:
```bash
npm install
```

If you encounter an error while running npm install, you can use the command 
```bash 
npm install --legacy-peer-deps 
```

as a replacement for npm install.
The command npm install --legacy-peer-deps is used to address issues related to installing dependencies in a Node.js project when versions of the dependent packages are not compatible with each other. 

4. Run front-end
```bash
npm start
```

### Building
To build the project, follow these steps:

1.Build the backend using `Visual Studio Code` or the `command line`.

2.Build the frontend:

```bash
npm run build
```
### Running
To run the project, use these commands:

1. Start the backend using `Visual Studio Code` or the `command line`.

2. Start the frontend:

```bash
npm start
```
# Screenshots

# License
[MIT](https://github.com/ncc-project/metasign/blob/dev/LICENSE)
