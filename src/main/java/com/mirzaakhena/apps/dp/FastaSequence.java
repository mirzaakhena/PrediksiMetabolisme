package com.mirzaakhena.apps.dp;

import java.io.*;

class FastaSequence extends Sequence
	{
	private String filename; 

	public FastaSequence()
		{
		super();		// calls constructor of the superclass
		filename = new String();
		}

	public void readFasta(String fastaFile, int number) throws ErrorInSequenceException, FileNotFoundException, IOException
		{
		if (getLength() != 0)	throw new ErrorInSequenceException("FastaSequence.readFasta() is called twice for the same object.\nAlternatively you might declare a new object.");

		int c;
		filename = fastaFile;				
		File f = new File (filename);
		int size = (int) f.length();			// Check file size
		FileReader in = new FileReader(fastaFile);	// text-file: use FileReader instead of FileInputStream.
		System.out.println("reading sequence " + number + " in file " + fastaFile + "...");
       		for (int chars_read=0; chars_read<size;)
			{
			c = in.read();
			chars_read++;
			if (c == '>')
				{
				if (--number == 0)	
					{
					while ((c=in.read()) != ' ' && chars_read<size)		{chars_read++; id+=(char)c;}
					chars_read++;
					while ((c=in.read()) != '\n' && chars_read<size)	chars_read++; 
					chars_read++;
					while ((c=in.read()) != '>' && chars_read<size)	 
						{
						chars_read++;
						if (c <= 'z' && c >= 'a')	c -= ('z'-'Z');
		       				switch (c)
							{
							case '\n': case '\t': case ' ':	break;
							case 'J': case 'O': case 'U':   
								throw new ErrorInSequenceException("ErrorInSequenceException: Illegal character '" +(char)c+ "' in the Fastafile " + filename);
							default:	insLetter((char)c);
							}
						}
					chars_read = size;	
					}
				}
			}   // end of  for (int chars_read=1; chars_read<size; chars_read++)
		in.close();
		if (id.length() == 0 || getLength() == 0)	throw new ErrorInSequenceException("ErrorInSequenceException: Bad Fastafile " + filename + ".");
		}
	}
