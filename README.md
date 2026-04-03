# portfolio-api-test

A production-grade REST API testing framework built with RestAssured, Cucumber, TestNG, JUnit, and ExtentReport. This project is structured as a reference implementation for teams and individuals learning how to design and organize a professional API automation framework in Java.

---

## Author

Gyanaprakash Khandual
GitHub: [gyanaprakashkhandual](https://github.com/gyanaprakashkhandual)

---

## Important Notice

This repository is open source. You are welcome to read, fork, study, and learn from the code structure, patterns, and implementation. However, **the APIs tested within this project are private and proprietary**. You are strictly prohibited from calling, consuming, integrating, or interacting with those APIs in any form. Please read the [LICENSE](./LICENSE.md) and [SECURITY](./SECURITY.md) documents for full details.

---

## Technology Stack

| Tool          | Version | Purpose                                |
| ------------- | ------- | -------------------------------------- |
| Java          | 11+     | Core programming language              |
| Maven         | 3.8+    | Build and dependency management        |
| RestAssured   | 5.4.0   | HTTP client for API testing            |
| Cucumber      | 7.15.0  | BDD framework                          |
| TestNG        | 7.9.0   | Test runner and suite management       |
| JUnit         | 4.13.2  | Alternative test runner                |
| ExtentReports | 5.1.1   | HTML test reporting                    |
| Jackson       | 2.16.1  | JSON serialization and deserialization |
| Log4j2        | 2.22.1  | Logging                                |

---

## Prerequisites

Before running this project, ensure the following are installed on your machine:

- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.8 or higher
- Git

Verify your setup:

```bash
java -version
mvn -version
git --version
```

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/gyanaprakashkhandual/portfolio-api-test.git
cd portfolio-api-test
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Configure the Environment

The framework supports three environments: `dev`, `qa`, and `prod`. Configuration files are located at:

```
src/test/resources/config/
├── config.properties       # Base configuration (shared across all environments)
├── dev.properties          # Development environment overrides
├── qa.properties           # QA environment overrides (default)
└── prod.properties         # Production environment overrides
```

Open the relevant `.properties` file and update the values to match your own API setup. The `base.url`, `api.version`, and `auth.token` fields are the minimum required entries.

### 4. Run the Tests

Run with the default QA environment:

```bash
mvn clean test
```

Run against a specific environment:

```bash
mvn clean test -Denv=dev
mvn clean test -Denv=qa
mvn clean test -Denv=prod
```

Run by Cucumber tag:

```bash
mvn clean test -Dcucumber.filter.tags="@Smoke"
mvn clean test -Dcucumber.filter.tags="@Regression"
mvn clean test -Dcucumber.filter.tags="@UserAPI and @Smoke"
mvn clean test -Dcucumber.filter.tags="not @WIP"
```

Run using the JUnit runner only:

```bash
mvn clean test -Dtest=JUnitRunner
```

---

## Project Structure

```
portfolio-api-test/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/framework/
│       │       ├── base/               # BaseTest with RestAssured spec initialization
│       │       ├── config/             # ConfigReader singleton for environment properties
│       │       ├── constants/          # API endpoint constants
│       │       ├── hooks/              # Cucumber Before/After hooks with ExtentReport wiring
│       │       ├── pojo/               # Plain Java model classes (request and response)
│       │       ├── reports/            # ExtentReportManager (thread-safe, timestamped output)
│       │       ├── runners/            # TestNGRunner and JUnitRunner
│       │       ├── stepdefinitions/    # Cucumber step definition classes
│       │       └── utils/             # RequestBuilder, JsonUtils, LoggerUtils
│       └── resources/
│           ├── config/                 # Environment-specific .properties files
│           ├── features/               # Cucumber .feature files organized by domain
│           ├── testdata/               # JSON request body payloads
│           ├── cucumber.properties
│           └── testng.xml              # TestNG suite configuration
├── reports/
│   └── extent/                         # Generated HTML reports (auto-created at runtime)
├── logs/                               # Log4j2 output
├── pom.xml
├── README.md
├── LICENSE.md
├── CODE_OF_CONDUCT.md
├── CONTRIBUTING.md
└── SECURITY.md
```

---

## Running Reports

After each test run, an ExtentReport HTML file is generated automatically under:

```
reports/extent/TestReport_<timestamp>.html
```

Open the file in any browser to view detailed test results, including passed, failed, and skipped scenarios with logs and environment metadata.

---

## Tagging Strategy

| Tag           | Purpose                                     |
| ------------- | ------------------------------------------- |
| `@Smoke`      | Critical path tests; run on every build     |
| `@Regression` | Full test suite; run on scheduled pipelines |
| `@UserAPI`    | All user domain tests                       |
| `@WIP`        | Work in progress; excluded from CI runs     |

---

## CI/CD

A GitHub Actions workflow is included at `.github/workflows/api-tests.yml`. It runs the regression suite across `dev` and `qa` environments on every push to `main` or `develop`, and on a daily schedule.

Test reports are uploaded as build artifacts and retained for seven days.

---

## What You Can Do With This Repository

You are free to:

- Read and study the code, structure, and patterns
- Fork the repository
- Clone it locally for learning and reference
- Submit issues and pull requests
- Adapt the framework structure for your own projects
- Share and reference it in your learning material

You are strictly prohibited from:

- Calling, consuming, or integrating the APIs tested within this project
- Using the API endpoints, base URLs, tokens, or credentials in any form
- Reverse engineering or attempting to access the underlying API services

Please review the [LICENSE](./LICENSE.md) document for the complete legal terms.

---

## Contributing

Contributions are welcome. Please read [CONTRIBUTING.md](./CONTRIBUTING.md) before submitting a pull request.

---

## Code of Conduct

This project follows a Contributor Code of Conduct. See [CODE_OF_CONDUCT.md](./CODE_OF_CONDUCT.md) for details.

---

## Security

If you discover a security vulnerability in this repository, please follow the responsible disclosure process described in [SECURITY.md](./SECURITY.md). Do not open a public issue.

---

## License

The source code in this repository is licensed under a custom open source license. The APIs, endpoints, and credentials are explicitly excluded from any permissions granted. See [LICENSE.md](./LICENSE.md) for the full terms.
