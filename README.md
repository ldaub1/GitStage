# GitStage

! USAGE FOR DUMMIES:
Run "Main.java". Click the "Run" button above the main method. This will make a project file and initialize the repo for it with the Git.initializeRepo method. Then it makes a few files inside of the newly created project folder. Then it runs the refresh method, which recursively goes through all relevant project files inside the project repository and makes a blob file for them and adds them to the index file. Then it calls the clear method to delete them.

This is a simple 'Git' recreation project. The functionality currently includes initializing repositories and hashing/storing files within them.

FILE STRUCTURE:
Main.java
    Main class, what you run in order to use the functionality. You can use the methods however you want, this is just for quick starting.
Git.java
    Includes the most important methods for creating and deleting repos. Most methods just call on helper methods from Utils.java. 
Utils.java
    The utilities class. This includes methods mainly for file/repo creation/modification/deletion. It also contains methods for compressing and decompressing code.
Tester.java
    The tester class. This creates 1000 repos and then deletes them. It takes about one second. If this isn't sufficient testing, I don't know what is. It also has methods to check for file existance within a repository and stuff. 

USAGE:
    To initialize a repository use the method Git.initializeRepo(repoPath). The repoPath should just be the name of what you want to call the folder. If you want to initialize it inside of another folder, you can do that by extending the path like so: Git.initializeRepo(anotherFolder/whateverYouWantToCallYourRepo).
    To delete a repository, use the method Git.deleteRepo(repoPath). This is pretty self-explanatory. It works recursively.

    To stage a file, use Git.makeBlob(filePath, repoPath). You need to pass in the file that you want to BLOB as well as the folder that it is in. 

    The Util class contains many helpful methods for your convenience. You are encouraged to read the method descriptions. 

EDGE CASES:
    The Utils will mostly all work or not, but they typically won't throw an error. They return true if they work and false if they don't. They probably won't work if you input a nonexistent file path or name. 
    The Git class is also similarly constructed. Initializing should always work, unless you input an empty string as the file name. Deleting should always work, as long as you input a real file. 