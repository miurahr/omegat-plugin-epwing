# Change Log
All notable changes to this project will be documented in this file.

## [Unreleased]

## [v2.2.1]

### Changed
* Bump versions
  - eb4j@2.2.1(#42)
  - actions/setup-java@2.3.0
  - spotless@5.14.3
* Use prefix search(#43)
* Allow appendix/furoku beside with catalogs(#43)

### Fixed
* slf4j configuration: duplicated backend

## [v2.2.0]

### Added
* Activate appendix support(#31)

### Changed
* Bump versions
  - spotless@5.14.2
  - spotbugs@4.7.2
  - actions/upload-artifact@2.2.4
* checkstyle: Adjust coding sytles
* findbugs: Suppress warnings

## [v2.1.2]

### Added
* Publish jar archive to github releases

### Changed
* Bump versions
  - omegat-gradle@1.5.3
  - actions/setup-java@2.1.0
  - spotless@5.13.0
  - commons-io@2.9.0
  - junit@5.7.2
  - spotbugs@4.7.1
  - slf4j@1.7.30
  - groovy@3.0.8
  - coveralls@2.12.0
* Bump EB4J@2.1.8 from MavenCentral
* Drop azure/github repository publish

## [v2.1.1]

### Changed

* Bump EB4J@2.1.6
* Use gradle git-version plugin for versioning
* Use Github or Azure packages repository for EB4J

## [v2.1.0]

### Add

* Set plugin metadata
* Support GAIJI showing by Unicode alternative

### Changed

* Update dependency to EB4J 2.1.4
* Gradle: Migrate to Kotlin DSL
* Gradle: Use Gradle's OmegaT plugin v1.4.2
* Auto versioning with Git tag

## [v2.0.2]

### Fixed

* Fix some characters become number.
* Enlarge buffer size not to chop long articles.


## [v2.0.1]

### Fixed

* Fix plugin load error.
* Fix plugin package.


## [v2.0.0]

### Add

* CI/CD: github actions scripts to automate test and release.
* Add unit tests.
* Add coveralls coverage monitor.

### Changed

* Semantic versioning.
* move package path to `tokyo.northside.omegat`
* change gradle version to 6.6.1
* Bump dependencies versions.


## [v1.1]
### Changed
- Use plugin-skeleton's Gradle build framework.

  - Drop stub and use remote OmegaT repository.

  - Use maven Standard directory structure.
  
## v1.0

### Add
- First working release.

[Unreleased]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.2.1...HEAD
[v2.2.1]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.2.0...v2.2.1
[v2.2.0]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.1.2...v2.2.0
[v2.1.2]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.1.1...v2.1.2
[v2.1.1]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.1.0...v2.1.1
[v2.1.0]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.0.2...v2.1.0
[v2.0.2]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.0.1...v2.0.2
[v2.0.1]: https://github.com/miurahr/omegat-plugin-epwing/compare/v2.0.0...v2.0.1
[v2.0.0]: https://github.com/miurahr/omegat-plugin-epwing/compare/v1.1...v2.0.0
[v1.1]: https://github.com/miurahr/omegat-plugin-epwing/compare/v1.0...v1.1
