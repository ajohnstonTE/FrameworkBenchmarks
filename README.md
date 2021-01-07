# Welcome to [TechEmpower Framework Benchmarks (TFB)](http://www.techempower.com/benchmarks/)

[![Build Status](https://github.com/TechEmpower/FrameworkBenchmarks/workflows/build/badge.svg?branch=master&event=push)](https://github.com/TechEmpower/FrameworkBenchmarks/actions?query=workflow%3Abuild+branch%3Amaster)

If you're new to the project, welcome! Please feel free to ask
questions [here](https://github.com/TechEmpower/FrameworkBenchmarks/issues/2978). We encourage new frameworks and
contributors to ask questions. We're here to help!

This project provides representative performance measures across a wide field of web application frameworks. With much
help from the community, coverage is quite broad and we are happy to broaden it further with contributions. The project
presently includes frameworks on many languages including `Go`, `Python`, `Java`, `Ruby`, `PHP`, `C#`, `F#`,`Clojure`
, `Groovy`, `Dart`, `JavaScript`, `Erlang`, `Haskell`, `Scala`, `Perl`, `Lua`, `C`, and others. The current tests
exercise plaintext responses, JSON serialization, database reads and writes via the object-relational mapper (ORM),
collections, sorting, server-side templates, and XSS counter-measures. Future tests will exercise other components and
greater computation.

[Read more and see the results of our tests on cloud and physical hardware](http://www.techempower.com/benchmarks/). For
descriptions of the test types that we run, see the
[test requirements section](https://github.com/TechEmpower/FrameworkBenchmarks/wiki/Project-Information-Framework-Tests-Overview)
.

If you find yourself in a directory or file that you're not sure what the purpose is, checkout
our [file structure](https://github.com/TechEmpower/FrameworkBenchmarks/wiki/Codebase-File-Structure) in our
documentation, which will briefly explain the use of relevant directories and files.

## Quick Start Guide

### Prerequisites

To get started developing you'll need to install [docker](https://docs.docker.com/install/).

### Running a Test Implementation

1. Download the latest [`tfb_toolset`](https://github.com/TechEmpower/TFBToolset) binary:

        $ curl -SO https://github.com/TechEmpower/TFBToolset/releases/latest/download/tfb_toolset

2. Optional: Move `tfb_toolset` onto the path:

        $ mv tfb_toolset /usr/bin

2. `tfb_toolset` searches 3 locations for a `FrameworkBenchmarks` repository, so do *ONE* of the following:
    * Set `TFB_HOME` environment variable

            $ export TFB_HOME=/path/to/FrameworkBenchmarks
   
    * Clone to the default `TFB_HOME` of `~/.tfb`
    
            $ clone --depth=1 https://github.com/TechEmpower/FrameworkBenchmarks ~/.tfb
    
    * Execute from `TFB_HOME` as the current directory:

            $ cd /path/to/FrameworkBenchmarks

3. Run a test:

        $ tfb_toolset --mode verify --test gemini

## Add a New Test

TODO

## Resources

### Official Documentation

Our official documentation can be found in the [wiki](https://github.com/TechEmpower/FrameworkBenchmarks/wiki). If you
find any errors or areas for improvement within the docs, feel free to open an issue in this repo.

### Live Results

Results of continuous benchmarking runs are available in real time [here](https://tfb-status.techempower.com/).

### Data Visualization

If you have a `results.json` file that you would like to visualize, you
can [do that here](https://tfb-status.techempower.com/share). You can also attach a `runid` parameter to that url
where `runid` is a run listed on [tfb-status](https://tfb-status.techempower.com) like
so: https://www.techempower.com/benchmarks/#section=test&runid=fd07b64e-47ce-411e-8b9b-b13368e988c6

## Contributing

The community has consistently helped in making these tests better, and we welcome any and all changes. Reviewing our
contribution practices and guidelines will help to keep us all on the same page.
The [contribution guide](https://github.com/TechEmpower/FrameworkBenchmarks/wiki/Development-Contributing-Guide) can be
found in the [TFB documentation](https://github.com/TechEmpower/FrameworkBenchmarks/wiki).

Join in the conversation on our [mailing list](https://groups.google.com/forum/?fromgroups=#!forum/framework-benchmarks)
, on [Twitter](https://twitter.com/tfbenchmarks), or chat with us on [Freenode](https://webchat.freenode.net/)
at `#techempower-fwbm`. 
