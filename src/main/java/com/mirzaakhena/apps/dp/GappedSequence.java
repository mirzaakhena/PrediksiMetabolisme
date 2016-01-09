package com.mirzaakhena.apps.dp;

import java.io.*;

class GappedSequence extends Sequence
	{
	public GappedSequence(int maxLength)
		{
		super(maxLength);
		}

	    // now there are 26 letters and '-' accepted by insLetter(char letter)
	    // also note that the   insLetter     -method differs significantly from its overridden method
	    // in the order of the inserted letters.

	public void insLetter(char letter) throws ErrorInSequenceException 
		{
		if (length < maxSeqLength)	
			{
			if ((letter >= 'A' && letter <= 'Z') || letter == '-')	
				{
				for (int i=length; i>0;)	letters[i] = letters[--i];
				letters[0] = letter;
				length++;
				}
			else throw new ErrorInSequenceException("ErrorInSequenceException: Illegal character '" +letter+ "' in the sequence " + id);
			}
		else throw new ErrorInSequenceException("ErrorInSequenceException: Sequence "+id+" is too long.\nmaxSeqLength = "+maxSeqLength);
		}
	}


