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
- [.NET Core 2.1 SDK](https://dotnet.microsoft.com/en-us/download/dotnet/2.1) installed.
- [ASP.NET Runtime 2.0.9](https://dotnet.microsoft.com/en-us/download/dotnet/2.0) installed.
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

- Locate the **local-timesheet.sql** file in your local environment.
- Rename it to **local-timesheet.sql**
8. Update the `appsettings.json` file:

- Open the `appsettings.json` file in the backend project.

- Locate the `ConnectionStrings` section.

- Update the **Default** connection string to match your local database information:



```json
{
  "ConnectionStrings": {
    "Default": "Server=servername; Database=local-ncc-project; User ID=yourUserId;Password=yourPassword;"
  },
  // ... other settings ...
}
```

9. Press `F5` or select `Debug` > `Start Debugging` to run the backend.

### Frontend Setup
1. Open Front-end repository
```bash
cd C:\Users\dell\Documents\CSharpDotnet\timesheet\ncc-erp-project\angular
```
- run code
```bash
code .
```

2. Install Angular CLI 7.1.3 globally:

```bash

npm install -g @angular/cli@7.1.3

```
3. Install frontend dependencies:
```bash
npm install
```
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
