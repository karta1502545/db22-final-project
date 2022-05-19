# Latency Estimator

## Prerequisite

- Java Development Kit 8+
- Maven

## Data Set Format Requirements

Both feature and latency files should be in CSV format.

### Features

Feature file should include the following fields:

- `Transaction ID`: Long Type
- `Start Time`: Long Type

### Latencies

Latency file should include the following fields:

- `Transaction ID`: Long Type
- `Latency`: Long/Double Type

## Build

```
> mvn package
```

This command generates a file called `latency-estimator-[version number]-jar-with-dependencies.jar`, which includes all the dependencies that this program needs and information for starting the program, in `target` directory.

## Run

Use the following command to run:

```
> java -jar [Jar File] -c [Config File] [Subcommands]
```

- `[Jar File]`: the path to the built jar file. This is usually `target/estimator-[version number]-jar-with-dependencies.jar`
- `[Config File]`: (Optional) the path to the configuration file. The default is `./config.toml`.

Note that the path to a configuration file is optional. We assume the configuration file is called `config.toml` and is placed in the working directory. If it is not the case, you can use `-c` option to specify the location.

Two subcommands available:

- `train`: training a model and save it to a file
- `test`: testing a pre-trained model

### Training

```
> java -jar [Jar File] train [Feature File] [Latency File] [Model Save Path]
```

- `[Feature File]`: the path to the feature file
- `[Latency File]`: the path to the latency file
- `[Model Save Path]`: the path to save the model


### Testing

```
> java -jar [Jar File] test [Feature File] [Latency File] [Model Save Path]
```

- `[Feature File]`: the path to the feature file
- `[Latency File]`: the path to the latency file
- `[Model File]`: the path to a pre-trained model
