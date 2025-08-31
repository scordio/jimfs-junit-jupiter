# Limitations

Jimfs JUnit Jupiter only supports annotated fields or parameters of type `Path`, since Jimfs is a non-default file
system and `File` instances can only be associated with the default file system.

In addition, compared to the configuration options that Jimfs provides, Jimfs JUnit Jupiter offers a simplified
interface to keep usage straightforward.

In case something is missing for your use case,
please [:fontawesome-brands-github: raise an issue](https://github.com/scordio/jimfs-junit-jupiter/issues/new)!
