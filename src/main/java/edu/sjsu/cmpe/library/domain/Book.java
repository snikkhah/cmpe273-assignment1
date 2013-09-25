package edu.sjsu.cmpe.library.domain;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Review;

public class Book {
    private Long isbn;
    private String title;
    private String publicationdDate;
    private String language;
    private int numPages;
    private String status;
    private ArrayList<Author> authors=new ArrayList<Author>();
    private ArrayList<Review> reviews=new ArrayList<Review>();

    // add more fields here

    /**
     * @return the isbn
     */
    public Book()
    {
    	language="Not Defined";
    	numPages=0;
    	status="Available";
    }
    
    public Long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(Long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

	public String getPublicationdDate() {
		return publicationdDate;
	}

	public void setPublicationdDate(String publicationdDate) {
		this.publicationdDate = publicationdDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int	 getNumPages() {
		return numPages;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LinksDto getAuthors() {
		LinksDto links = new LinksDto();
		int i = 1;
		while (i<=authors.size()){
		links.addLink(new LinkDto("view-author","/books/"+isbn+"/authors/" + i, "GET"));
		i++;
		}
		//“rel”: “view-author”, “herf”: “/books/1/authors/1”, “method”: “GET”
		return links;
	}
	@JsonIgnore
	public ArrayList<Author> getAuthorsArray(){
		return authors;
	}
	
	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}
	
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
}
