package bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Account.
 *
 * @author ragnarokkrr
 */
@Entity
public class Account {

    @OneToMany(mappedBy = "account")
    private Set<Bookmark> bookmarks = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    private String password;
    private String username;

    public Account(String password, String username) {
        this.password = password;
        this.username = username;
    }

    Account() {
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account that = (Account) o;

        return Objects.equal(this.bookmarks, that.bookmarks) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.password, that.password) &&
                Objects.equal(this.username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookmarks, id, password, username);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("bookmarks", bookmarks)
                .add("id", id)
                .add("password", password)
                .add("username", username)
                .toString();
    }
}
