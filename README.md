# Installation

```sh
gradle wrapper
./gradlew build
```

## Git hooks

### pre commit message

Add to `./.git/hooks/prepare-commit-message` the following

```js
#!/usr/bin/env node

/**
 * --no-verify bypass pre-commit and commit-msg hooks
 */
const fs = require("fs");

const { exec } = require("child_process");
// Check if commit has issue number
const regex_commit_issue_number = /^\s*[\-\w]*\d/;
// Extract issue number from branch
const regex_branch_issue_number = /([\-\w]*?\-\d+)(([-_])(.*\S))?/;
// Check protected branches
const regex_protected_branch = /(main)|(develop)/;
// Automated git messages
const regex_git_commit_messages = /Merge\sbranch|Rebase|This\sreverts\scommit/;

const commitMessageLocation = process.argv[2];

let jiraIssue;

exec("git branch --show-current", (err, stdout, stderr) => {
 if (!stderr && !err) {
  if (!regex_branch_issue_number.test(stdout)) {
   if (regex_protected_branch.test(stdout)) {
    const protectedMessage = "\x1b[31mYou are trying to push into a protected branch: \x1b[1m" + stdout + "\x1b[0m";
    process.stdout.write(protectedMessage);
    process.stdout.write("\x1b[31m Use --no-verify flag to bypass this error.\x1b[0m");
    process.exit(1);
   }
   process.stdout.write("\x1b[93m No Jira issue number found in branch name " + stdout + " \x1b[0m");
   process.exit(0);
  } else {
   const commitMessage = fs.readFileSync(commitMessageLocation, { encoding: "utf-8" });
   if (regex_git_commit_messages.test(commitMessage)) {
    process.stdout.write("\x1b[93m This seems to be an automated message from git. Skipping pre-commit-hook\x1b[0m");
    process.exit(0);
   }

   if (!regex_commit_issue_number.test(commitMessage)) {
    console.log("\x1b[93m No Jira issue description specified in the commit message, adding automated description\x1b[0m");
   }

   const [branchName, branchIssueNumber, _, __, branchdescription] = stdout.match(regex_branch_issue_number);
   const cleanBeanchDescription = branchdescription.replaceAll(/[-_]/gi, " ");
   jiraIssue = `${branchIssueNumber} ${cleanBeanchDescription}\n\n`;
   const newCommitMessage = jiraIssue + commitMessage;
   try {
    fs.writeFileSync(commitMessageLocation, newCommitMessage);
    process.stdout.write("\x1b[32m Added issue description to commit message.\x1b[0m");
    // file written successfully
   } catch (err) {
    process.stderr.write(err);
    process.stdout.write("\x1b[31m There was an error editing the commit mesage. Please use --no-verify to bypass this hook\x1b[0m");
    process.exit(1);
   }
  }
 }

process.exit(0);
});
```
