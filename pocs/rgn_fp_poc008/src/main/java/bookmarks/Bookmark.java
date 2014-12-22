package bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Bookmark.
 *
 * @author ragnarokkrr
 */
@Entity
public class Bookmark {

    @JsonIgnore
    @ManyToOne
    private Account account;

    @Id
    @GeneratedValue
    private Long id;

    private String uri;
    private String description;

    Bookmark() {
    }

    public Bookmark(Account account, String uri, String description) {
        this.account = account;
        this.uri = uri;
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookmark that = (Bookmark) o;

        return Objects.equal(this.account, that.account) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.uri, that.uri) &&
                Objects.equal(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(account, id, uri, description);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("account", account)
                .add("id", id)
                .add("uri", uri)
                .add("description", description)
                .toString();
    }
}
