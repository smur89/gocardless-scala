# GoCardless Scala
[![Build status](https://badge.buildkite.com/ce88556b42459bcc346588abcf44eee6a0d5e9f8f567dcdc71.svg)](https://buildkite.com/smur89/gocardless-scala) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/1e18b04222dc465e93ca1f5f6c6a8da6)](https://www.codacy.com/manual/smur89/gocardless-scala?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=smur89/gocardless-scala&amp;utm_campaign=Badge_Grade)

An asynchronous scala client library for the GoCardless API

# Buildkite Agent
The buildkite agent  can be run locally using the following command:
```dockerfile
docker run \
    -e BUILDKITE_AGENT_TOKEN=$AGENT_TOKEN \
    -v $HOME/.ssh/:/root/.ssh \
    -v /var/run/docker.sock:/var/run/docker.sock \
    buildkite/agent
```

# Contributing
After cloning the repo, please run:
```
pip install pre-commit # If not installed
pre-commit install
```
