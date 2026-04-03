# Security Policy

## Overview

The security of this project matters. This document explains what is in scope for security reporting, how to report a vulnerability responsibly, and what you should never do in the context of this repository.

This repository contains an open source API testing framework. The APIs, endpoints, credentials, tokens, and backend services referenced in the test code are private infrastructure. Any interaction with those APIs outside of the intended testing context is unauthorized and a violation of the project license.

---

## Supported Versions

Security reports are accepted for the most recent version of the code on the `main` branch.

| Branch     | Supported |
| ---------- | --------- |
| main       | Yes       |
| All others | No        |

---

## Reporting a Security Vulnerability

If you discover a security vulnerability in the framework code itself — such as insecure handling of credentials in configuration files, unsafe deserialization, log injection, or similar issues — please report it responsibly using the process below.

**Do not open a public GitHub Issue to report a security vulnerability.** Public disclosure before a fix is in place puts all users of this repository at risk.

### How to Report

1. Send a detailed report to the maintainer via GitHub. Navigate to the profile at [gyanaprakashkhandual](https://github.com/gyanaprakashkhandual) and use the available contact method.

2. Include the following in your report:
   - A clear description of the vulnerability and the potential impact
   - The file or files affected and the relevant code location
   - Step-by-step instructions to reproduce the issue
   - Any suggested fix or mitigation, if you have one

3. You will receive an acknowledgment within 72 hours of submission.

4. The maintainer will investigate, develop a fix, and coordinate a disclosure timeline with you. Credit will be given to responsible reporters in the release notes unless you prefer to remain anonymous.

---

## What is Not In Scope

The following are explicitly outside the scope of this security policy and will not be treated as valid vulnerability reports:

- Attempts to probe, scan, or test the APIs referenced within this project. Those APIs are private. Any unauthorized interaction with them is a violation of the license and potentially applicable law, not a contribution to this project's security.
- Reports about missing security headers, rate limiting, or authentication behavior of the private APIs. You do not have authorization to interact with those services.
- Social engineering or phishing attempts against the maintainer
- Theoretical vulnerabilities with no demonstrated impact
- Reports generated purely by automated scanners without manual verification

---

## Strict Prohibition on API Probing

The APIs tested in this framework are private property. They are not a bug bounty target. They are not open for security research. They are not public APIs.

The following actions are strictly prohibited and will be treated as unauthorized access:

- Sending any HTTP request to the API endpoints referenced in this repository without explicit written permission from the maintainer
- Attempting to discover, enumerate, or map the API surface beyond what is documented in this repository
- Using the authentication tokens or credentials found in this repository to authenticate against any service
- Intercepting, monitoring, or analyzing traffic between the test framework and the API endpoints
- Exploiting or testing any vulnerability found in the private API infrastructure

Violation of these restrictions may result in reporting to relevant authorities in addition to legal action.

---

## Credential and Token Exposure

If you discover that a real authentication token, API key, secret, or password has been accidentally committed to this repository:

1. Do not use the credential in any way.
2. Report it immediately using the responsible disclosure process above.
3. The maintainer will rotate the credential and purge the commit history as quickly as possible.

Even if a credential is publicly visible in a repository, using it without authorization is illegal in most jurisdictions under computer misuse and unauthorized access laws.

---

## Responsible Disclosure Commitment

The maintainer commits to the following upon receiving a responsible security disclosure:

- Acknowledge receipt within 72 hours
- Provide a status update within 7 days
- Work toward a fix within 30 days for confirmed vulnerabilities
- Credit the reporter in the changelog or release notes upon resolution, unless anonymity is requested
- Not pursue legal action against researchers who follow this responsible disclosure process in good faith

---

## Thank You

Responsible security researchers who take the time to report issues privately make open source safer for everyone. This effort is genuinely appreciated.
