package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;

import edu.sjsu.cmpe.library.domain.Author;

public class AuthorsDto extends LinksDto{
	ArrayList<Author> authors = new ArrayList<Author>();
	public AuthorsDto(ArrayList<Author> authors) {
		// TODO Auto-generated constructor stub
		super();
		this.setAuthors(authors);
	}
	
	public ArrayList<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

}
