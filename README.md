# SystemDependentFiles
A little java library to get System and user dependent files.
With this well-documented little java library you can easily get system and user specific files,
for example with ```SystemDependentFile.getUserFile(String relativePath)``` you can get a file within 
the users home directory matching the given path. For the user "joe" on a mac, it would return the file
```/Users/joe/path```.
