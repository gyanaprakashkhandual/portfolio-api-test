# Contributing

Thank you for your interest in contributing to this project. Contributions of all kinds are welcome, including bug reports, improvements to documentation, suggestions for better test patterns, and code enhancements.

Please take a few minutes to read this guide before submitting anything. Following these guidelines helps maintain the quality of the repository and makes the review process smoother for everyone.

---

## Before You Start

Read the [LICENSE](./LICENSE.md) carefully. The source code of this framework is open for contribution. The APIs tested within the project are private and are strictly off-limits. Do not submit any contributions that involve calling, modifying, or exposing the tested API endpoints, credentials, or configuration values.

---

## How to Contribute

### Reporting a Bug

If you find a bug in the framework code, please open a GitHub Issue with the following information:

- A clear and descriptive title
- A description of the problem and the expected behavior
- Steps to reproduce the issue
- The environment details: Java version, Maven version, and operating system
- Any relevant error messages or stack traces

Before opening a new issue, search the existing issues to avoid duplicates.

### Suggesting an Enhancement

Enhancement suggestions are welcome. Open a GitHub Issue and include:

- A clear description of the enhancement and the problem it solves
- Any examples of how similar frameworks or tools handle this
- Whether you are willing to implement the change yourself

### Submitting a Pull Request

Follow these steps to submit a code contribution:

**Step 1: Fork the repository**

Click the Fork button on the GitHub repository page to create your own copy.

**Step 2: Clone your fork**

```bash
git clone https://github.com/YOUR_USERNAME/portfolio-api-test.git
cd portfolio-api-test
```

**Step 3: Create a feature branch**

Always branch from `main`. Use a descriptive branch name.

```bash
git checkout -b feature/add-response-schema-validation
git checkout -b fix/config-reader-null-pointer
git checkout -b docs/update-readme-setup-steps
```

**Step 4: Make your changes**

Keep changes focused and minimal. One pull request should address one logical change.

Follow the code conventions described below before committing.

**Step 5: Run the tests**

Ensure that the existing test suite passes with your changes applied.

```bash
mvn clean test
```

If you are adding new functionality, include at least one corresponding test or feature file update that validates the behavior.

**Step 6: Commit your changes**

Write clear, descriptive commit messages. Follow this format:

```
type: short description of what changed

Optional longer explanation of why the change was made,
and any relevant context.
```

Types: `feat`, `fix`, `docs`, `refactor`, `test`, `chore`

Examples:

```
feat: add schema validation utility using JsonSchemaValidator
fix: resolve NullPointerException in ConfigReader when env not set
docs: clarify environment configuration steps in README
test: add scenario outline for paginated GET requests
```

**Step 7: Push and open a pull request**

```bash
git push origin feature/your-branch-name
```

Open a pull request from your fork to the `main` branch of this repository. Fill in the pull request template with:

- A summary of what the pull request does
- The issue it addresses (if applicable)
- How you tested the change

---

## Code Conventions

Adhere to the following standards to keep the codebase consistent:

**Java**

- Use Java 11 features where appropriate
- Follow standard Java naming conventions: `PascalCase` for classes, `camelCase` for methods and variables, `UPPER_SNAKE_CASE` for constants
- Keep methods short and focused on a single responsibility
- Add Log4j2 log statements at `INFO` level for key actions and `ERROR` level for failures
- Avoid hard-coding values; use `ConfigReader` or `EndpointConstants`

**Cucumber**

- Write feature files in plain, readable English
- Use tags consistently: `@Smoke`, `@Regression`, `@WIP`, and domain-level tags such as `@UserAPI`
- Use `Background` for shared pre-conditions within a feature file
- Use `Scenario Outline` with `Examples` for data-driven scenarios

**Test Data**

- Place all JSON request body payloads under `src/test/resources/testdata/`
- Organize by domain (for example, `testdata/user/`, `testdata/product/`)
- Never commit real credentials, tokens, or sensitive values

**Documentation**

- Update the README if your change introduces new behavior, configuration, or a new way to run tests
- Keep comments in code minimal and meaningful; prefer self-documenting method names

---

## What Not to Contribute

The following contributions will not be accepted:

- Changes that expose, modify, or reference the private API endpoints, credentials, or base URLs
- Code that introduces external API calls to the tested services from any context other than the existing test framework
- Commits containing authentication tokens, secrets, or personal credentials of any kind
- Changes that remove or weaken the API Restriction Clause in the license

---

## Questions

If you have a question before contributing, open a GitHub Issue with the label `question` and the maintainer will respond as soon as possible.
