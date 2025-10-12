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
Tester.java --> dont know what happened but this no longer exists ?????????? so i remade it to do the final test w/ wrapper
    The tester class. This creates 1000 repos and then deletes them. It takes about one second. If this isn't sufficient testing, I don't know what is. It also has methods to check for file existance within a repository and stuff. 

USAGE:
    To initialize a repository use the method Git.initializeRepo(repoPath). The repoPath should just be the name of what you want to call the folder. If you want to initialize it inside of another folder, you can do that by extending the path like so: Git.initializeRepo(anotherFolder/whateverYouWantToCallYourRepo).
    To delete a repository, use the method Git.deleteRepo(repoPath). This is pretty self-explanatory. It works recursively.

    To stage a file, use Git.makeBlob(filePath, repoPath). You need to pass in the file that you want to BLOB as well as the folder that it is in. 

    The Util class contains many helpful methods for your convenience. You are encouraged to read the method descriptions. 

EDGE CASES:
    The Utils will mostly all work or not, but they typically won't throw an error. They return true if they work and false if they don't. They probably won't work if you input a nonexistent file path or name. 
    The Git class is also similarly constructed. Initializing should always work, unless you input an empty string as the file name. Deleting should always work, as long as you input a real file. 


MAKING A TREE FILE: 
    The following methods are used to make a tree file: makeInitialWorkingList(), order(), getMaxSlashes(), maxRows(), getParentPath(), groupByParentPath(), createTreeHash(), buildTree(), 
    recursiveBuildTree(), recursiveBuildTreeHelper()

    In order to create a tree file all you have to do is run recursiveBuildTreeHelper()

    the helper methods are:  makeInitialWorkingList(), order(), getMaxSlashes(), maxRows(), getParentPath(), groupByParentPath(), createTreeHash(), buildTree()
        - makeInitialWorkingList() = reads the index file and makes a file that has exactly what the index file has except the word "blob" is now infront of each line. The private instance variable workingListAL is equal to the output of this method, which is an arraylist in which each element is one line from this working file. For ex, elmenent zero of workingListAL would be a string that is something like this: "blob 23229367832881236827168236 FolderName/file" 
        - order() = looks at the workingListAL and determines how many slashes (\) that each element in workingListAL has. The method then returns an ArrayList that states the number of slashes for each element in workingListAL based on element. For example, if the arraylist returned in order() has the number 2 in at index zero, then at index zero in workingListAL there is 2 slashes. 
        - getMaxSlashes() = looks at the arraylist created in order() and determines what is the most number of slashes 
        - maxRows() = creates an arraylist that contains each line in the workingFile that has the highest number of slashes. For ex: if the max number of slashes is 3, then maxRows() creates an arraylist in which each element has 3 slashes 
        - getParentPath() = given a string, it determines the parent path. ie. it returns everything before the last \
        - groupByParentPath() = creates a HashMap in which the key is the parent folder and the value is all the files that are in that parent folder. 
        - createTreeHash() = hashes all the files in a given folder, this will become the hash for the tree file for this folder 
        - buildTree() = where all the methods above (except makeInitialWorkingList()) are actually used! First, it creates a HashMap in which the key is a parent folder and the vaule is all the files in that parent folder. The HashMap is made for all paths that were identified as having the max number of slashes. It then creates a newWorkingList ArrayList that after the tree is created will be used to update the workinglist. It then loops through the workingListAL and determiens if, at a given index, the element has the max number of slashes. If it doesn't then it is added to the newWorkingList. It then creates an arraylist called parentFolders that consists of all the folders that meet the max slash criteria. It also creates another arraylist called filesInFolder that has all the files that are in a parent folder. The createTreeHash() method is then used to create a tree hash given the elements in filesInFolder for that parent folder. That hash, along with word "tree" and the name of the folder are added to the newWorkingList Array List. The workingFile file is then updated based on the content of newWorkingList ArrayList. Finally, workingListAL is updated to be the same as newWorkingList so that is updated for the next iteration. This method will return true if the tree file is created. 
    the main methods are: recursiveBuildTree(), and recursiveBuildTreeHelper()
      - both of these methods essentially just keep making tree files (calling buildTree()) UNTIL either workingListAL is size one (aka only has the root) OR the max slashes equals zero. Checks the workingListAL condition first. 

BUGS | (fixed very very basic like how it just fully wasn't working bc pc -> windows uh still dosnt work but works barely enough for my section -> note that when adding files not in folders it doesnt ever add the root tree to objects so the tree hash in index isnt there ._.)

buildCommit(String author, String message) | Builds the commit, taking in author / message creates commit file, adds to objects puts hash in head (genuinely cannot tell if im using the correct thing to build the tree here??? seems like it should just be recursiveBuildTree but readme says to use the helper which doesnt make sense? from the code itself it seems like it should be the normal one so that is what is used but pc -> mac broke everything so going with that)

GitTester
clearFiles(); | same as above but can no longer set repo name

testInit(gw); | initializes repo testing all cases (gw doesnt let you set names so this doesnt either -> all defaulted to "ProjectFolder" in gw)

testFileAddition(gw); | tests all the cases for adding + blobing files

testBuildCommit(gw); | testing 2 commits, references previous