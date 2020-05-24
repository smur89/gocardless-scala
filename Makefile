.PHONY: build bundle clean run shell test

build:
	docker-compose run --rm --no-deps sbt stage

bundle:
	docker-compose build gocardless

clean:
	docker-compose run --rm -T sbt clean
	docker-compose down

run:
	docker-compose run --rm -T --service-ports sbt server/run

shell:
	docker-compose run --rm --service-ports sbt shell

test:
	docker-compose run --rm -T sbt test
