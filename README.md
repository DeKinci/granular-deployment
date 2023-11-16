# Granular deployment

> What if a microservice was a library?

> What if a cluster was a code?

One of the problems in the development and maintenance of
systems with a microservice architecture is the difficulty of updating APIs while
maintaining backward compatibility and constant system availability.
The paper proposes a solution to the problem - a method of automated
deployment and management of a dependency graph of services by using the
capabilities of infrastructure such as deployment orchestration and
service mesh.

The proposed method was implemented in the form of an
integration library and a Kubernetes service that manages the
deployment and routing processes. The obtained test results show a
significant increase in the automation of an API update process.

## Tl;dr

Add a client library to a service - establishing a dependency.

The deployment system will handle rollout and restart order as well as versioning - similar to how it already does that for libraries.

Comes with a build time cluster configuration validation!
