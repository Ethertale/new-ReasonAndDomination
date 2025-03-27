package io.ethertale.reasonanddominationspringdefenseproject.exceptions;

public class ForumPostContentBlankOrEmptyComment extends RuntimeException {
    private final String postSlug;

    public ForumPostContentBlankOrEmptyComment(String postSlug) {
        super("Comment cannot be blank or empty for post: " + postSlug);
        this.postSlug = postSlug;
    }

    public String getPostSlug() {
        return postSlug;
    }
}
