# GitStage

This is a simple 'Git' recreation project. The functionality currently includes initializing repositories and hashing/storing files within them.

FILE STRUCTURE:
Main.java
    Main class, what you run in order to use the functionality. You can use the methods however you want, this is just for quick starting.
Git.java
    Includes the most important methods for creating and deleting repos. Most methods just call on helper methods from Utils.java. 
Utils.java
    The utilities class. This includes methods mainly for file/repo creation/modification/deletion. 
Tester.java
    The tester class. This creates 1000 repos and then deletes them. It takes about one second. If this isn't sufficient testing, I don't know what is. 

USAGE:
    To initialize a repository use the method Git.initializeRepo(repoPath). The repoPath should just be the name of what you want to call the folder. If you want to initialize it inside of another folder, you can do that by extending the path like so: Git.initializeRepo(anotherFolder/whateverYouWantToCallYourRepo).
    To delete a repository, use the method Git.deleteRepo(repoPath). This is pretty self-explanatory. It works recursively.

    To stage a file, use Git.makeBlob(filePath, repoPath). You need to pass in the file that you want to stage as well as the folder that it is in. This will do all of the stuff that Git does when you stage a file. You can also use Git.stageFile(same parameters) if you prefer. This is the exact same method as makeBlob. 

    The Util class contains many helpful methods for your convenience. You are encouraged to read the method descriptions. 

EDGE CASES:
    The Utils will mostly all work or not, but they typically won't throw an error. They return true if they work and false if they don't. They probably won't work if you input a nonexistent file path or name. 
    The Git class is also similarly robust. Initializing should always work, unless you input an empty string as the file name. Deleting should always work, as long as you input a real file. 