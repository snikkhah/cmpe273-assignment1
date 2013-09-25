package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;

import edu.sjsu.cmpe.library.domain.Review;

public class ReviewsDto extends LinksDto {

    private ArrayList<Review> reviews = new ArrayList<Review>();

    /**
     * @param book
     */
    public ReviewsDto(ArrayList<Review> reviews) {
	super();
	this.setReviews(reviews);
    }

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

}
