package otus.crm.model;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "client")
@NoArgsConstructor
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @Fetch(value = FetchMode.JOIN)
    private Address address;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.JOIN)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        if (address != null) {
            this.address = new Address(address.getId(), address.getAddress(), this);
        } else {
            this.address = null;
        }
        this.phones = getNewCollection(phones);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = new Address(address.getId(), address.getAddress(), this);
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public List<Phone> setPhones(List<Phone> phones) {
        return getNewCollection(phones);
    }

    private List<Phone> getNewCollection(Collection<?> collection) {
        return collectionToStream(collection).filter(ps -> ps instanceof Phone).map(Phone.class::cast).map(p -> new Phone(p.getId(), p.getNumber(), this)).collect(Collectors.toList());
    }

    public Stream<?> collectionToStream(Collection<?> collection) {
        return Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", " +
                "name='" + name + '\'' +
                ", address=" + Optional.ofNullable(address).orElse(new Address()).getAddress() +
                ", phones=" + collectionToStream(this.phones)
                .filter(ps -> ps instanceof Phone)
                .map(Phone.class::cast)
                .map(Phone::getNumber)
                .collect(Collectors.joining(", ")) +
                '}';
    }
}

