## ERP-PROJECT (Project Management Tool)
This is a project management tool used to manage company projects, project participants, progress, and project evaluations. The tool is built using .NET and Angular. The ERP-PROJECT tool is regularly managed and updated by the staff team of NCC.

-----

### Installation
1. Install the project dependencies:
- [ASP.NET Core SDK](https://dotnet.microsoft.com/download) (version 3.1.426)
- [Node.js](https://nodejs.org/) (LTS version)
- [Angular CLI](https://cli.angular.io/) (global installation)

2. Environment Setup
**Clone the project from the repository:**
```bash
$ git clone https://github.com/ncc-erp/ncc-erp-project
$ cd ncc-erp-project
```

### Run Application (ASP.NET) 

1. **Open apsnet-core folder in Visual Studio :**
   - Open the solution file projectManagement.sln in the aspnet-core folder using Visual Studio. 

2. **Choose the run mode:**
   - Select the run mode (Debug or Release) and enviroment (IIS Express or specific server) as needed. Edit the listening port (if necessary).

3. **Edit the listening port (if necssary):**
   - Open `launchSettings.json` in the `ProjectManagement.Web.Host/Properties`.
   - Find the entry corresponding to the project (ví dụ: `ProjectManagement.Web.Host`).
   - Change the value of `applicationUrl` to specify the port that the backend will listen on.

4. **Press F5 (or select Debug > Start Debugging) to run the project.**

application will be compiled and run in the enviroment you've chosen. Information about the listening port will appear in the Output window in Visual Studio.




