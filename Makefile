NAME=gatekeeper
VERSION=$(shell git rev-parse HEAD)
SEMVER_VERSION=$(shell git describe --abbrev=0 --tags 2> /dev/null || echo 'NO TAG WITH SEMVER FOUND')
REPO=quay.io/babylonhealth
DEPLOY_DEV_URL=http://dev-ai-deploy.babylontech.co.uk:5199/job/AI-deploy-dev/buildWithParameters
DEPLOY_STAGING_URL=http://dev-ai-deploy.babylontech.co.uk:5199/job/AI-deploy-staging/buildWithParameters

.PHONY: docker build run push pull tag-latest tag-semver deploy-dev deploy-staging
.DEFAULT_GOAL := build

docker:
	docker build -t $(REPO)/$(NAME):$(VERSION) .

push:
	docker push $(REPO)/$(NAME):$(VERSION)

pull:
	docker pull $(REPO)/$(NAME):$(VERSION)

tag-latest: pull
	docker tag $(REPO)/$(NAME):$(VERSION) $(REPO)/$(NAME):latest
	docker push $(REPO)/$(NAME):latest

tag-semver: pull
	@if docker run --rm -e DOCKER_REPO=babylonhealth/$(NAME) -e DOCKER_TAG=$(SEMVER_VERSION) $(REPO)/tag-exists; then \
		echo "Tag $(SEMVER_VERSION) already exists!" && exit 1; \
	else \
		docker tag $(REPO)/$(NAME):$(VERSION) $(REPO)/$(NAME):$(SEMVER_VERSION); \
		docker push $(REPO)/$(NAME):$(SEMVER_VERSION); \
	fi

deploy-dev: tag-latest
	@curl -vvv -XPOST "${DEPLOY_DEV_URL}?token=${JENKINS_DEV_TOKEN}&APP=${NAME}&VERSION=${VERSION}"

deploy-staging: tag-semver
	@curl -vvv -XPOST "${DEPLOY_STAGING_URL}?token=${JENKINS_STAGING_TOKEN}&APP=${NAME}&VERSION=${SEMVER_VERSION}"
