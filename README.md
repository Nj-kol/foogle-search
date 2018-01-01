                                      
#  Foogle Search
                           
This application indexes all the files from a supplied folder, and makes the contents of the file searchable. The search 
is on
a per word basis, i.e. it does not perform phrase queries. Also, the search is case insensitive.
The results are displayed by the descending order of the word frequency in the file. 
Each search result contains the following meta-data :
- The file where the word was found
- Number of time the words occurred in the particular file
- The exact lines where the word is present

### Procedure

The algorithm for crawling files is such that it will index all files in any sub-directory present under a parent,
no matter how deep the directories are nested. 
The program creates an inverted index like structure in-memory of all the files found by the crawling a directory. It 
then
uses this index to carry out the search, which makes the response time very fast. The index is not written to a 
persistent
store, therefore if the program terminates, the index needs to be re-created.

### Usage

java -jar <location of the jar>
For example- java -jar D:\foogle-search-0.0.1.jar
The program will prompt you for the location of the directory, after which it will commence the indexing procedure. 
Once the indexing process completes, the user will be asked for the search term.
The program is designed to carry out any number of searches, but indexing happens only once, such that if you need to
index a different directory, you either need to re-launch the program or launch another instance.
Note - This program is written with java 8 semantics, therefore please check the java version before running.
Foogle Search
This application indexes all the files from a supplied folder, and makes the contents of the file searchable. The search is on
a per word basis, i.e. it does not perform phrase queries. Also, the search is case insensitive.

The results are displayed by the descending order of the word frequency in the file.

Each search result contains the following meta-data :

The file where the word was found
Number of time the words occurred in the particular file
The exact lines where the word is present
Procedure
The algorithm for crawling files is such that it will index all files in any sub-directory present under a parent,
no matter how deep the directories are nested.

The program creates an inverted index like structure in-memory of all the files found by the crawling a directory. It then
uses this index to carry out the search, which makes the response time very fast. The index is not written to a persistent
store, therefore if the program terminates, the index needs to be re-created.

Usage
java -jar <location of the jar>
For example- java -jar D:\foogle-search-0.0.1.jar

The program will prompt you for the location of the directory, after which it will commence the indexing procedure.
Once the indexing process completes, the user will be asked for the search term.

The program is designed to carry out any number of searches, but indexing happens only once, such that if you need to
index a different directory, you either need to re-launch the program or launch another instance.

Note - This program is written with java 8 semantics, therefore please check the java version before running.
