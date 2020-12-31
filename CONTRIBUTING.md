# Contributing

When contributing to this repository, please first discuss the changes you wish to make by creating an issue.

## Styleguides

### Pull request (PR) naming
- The title of the PR should be self-explanatory
- Put the `WIP:` tag before the name of your PR if it is in progress
- Put the issue tag followed by the issue number at the beginning of the pull request, like ``RSV-6``
- After you submit your pull request, verify that all status checks are passing
- When merging, squash all commits into one. The commit message should be the same as the PR name

For example:
`WIP: [RSV-6] Add tables to reservation`

### Branch naming
Branch names should be written in kebab-case

Branch names should be composed of three parts:
- Applicable word
    - "feature" when the adding a new feature
    - "fix" when fixing a bug
    - "refactor" when refactoring
- Issue tag
- Name of the issue

For example:
`feature/CQTB-6/add-tables-to-reservation`

### Git commit message
- Use the present tense
- Use the imperative mood
- The commit message needs to be descriptive

For example:
`git commit -m "Update contributing.md"`

### Code style

All code is written using a code style based on our preference. Config file: ``QA-eclipse-formatter``


The project is configured to be formatted after each push with [formatter-maven-plugin](https://code.revelc.net/formatter-maven-plugin/).

If you are using Intellij, install ``Eclipse-Code-Formatter`` plugin and set the ``QA-eclipe-formatter.xml`` file
as the `Eclipse Java Formatter config file`. You can find the ``QA-eclipe-formatter.xml`` file at project's root.

It is recommended to install the `Save Actions` plugin, to format your file on save. If you use the plugin, enable: 
- Activate save actions on save
- Optimize imports
- Reformat file

Take note that your pipeline will fail if your code is not formatted correctly.

Manual code formatting

    mvn formatter:format

Manual code format validation

    mvn formatter:validate
    
### Documentation Styleguide
- Use [Markdown](https://www.markdownguide.org/basic-syntax/)

### Testing culture

Everything that might break must be tested. 

You must try to use Test Driven Development (TDD)

This project uses [JUnit5](https://junit.org/junit5/).

Please, follow this list when creating a test
- Tests must respect the `given_when_then` naming convention
- A test should be fast
- A test should be automated
- A test should be independent of the environment, time, connection, etc.
- A test must have only one reason to fail
- The code must respect the same standards of quality that apply to production code

## Definition of Done
A user story and feature is considered done when
- Unit tests passed
- Code reviewed and approved
- Acceptance criteria met
- Non-functional requirements met
- QA is done and passed
- CI checks passed
- Code merged in master after preceding criteria are met
