# Book-Index-Generator

This is a project for generating a book index generator, this index generator excludes the top 1000 words used in the english language and
makes an index of the remaining words.  For testing i included a few text files you can run the project on.

To achieve a fast processing speed i used virtual threads which are new in Java 19. Each thread recieves a line from the book and processes all the words from it 
to add it to the index.

