package mk.test.gamesbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_company")
public class InvolvedCompany implements Parcelable {

    @PrimaryKey
    @ColumnInfo
    private int id;
    @ColumnInfo
    private Company company;
    @ColumnInfo
    private boolean developer, porting, publisher, supporting;

    public InvolvedCompany() {}

    protected InvolvedCompany(Parcel in) {
        id = in.readInt();
        company = in.readParcelable(Company.class.getClassLoader());
        developer = in.readByte() != 0;
        porting = in.readByte() != 0;
        publisher = in.readByte() != 0;
        supporting = in.readByte() != 0;
    }

    public static final Creator<InvolvedCompany> CREATOR = new Creator<InvolvedCompany>() {
        @Override
        public InvolvedCompany createFromParcel(Parcel in) {
            return new InvolvedCompany(in);
        }

        @Override
        public InvolvedCompany[] newArray(int size) {
            return new InvolvedCompany[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isDeveloper() {
        return developer;
    }

    public void setDeveloper(boolean developer) {
        this.developer = developer;
    }

    public boolean isPorting() {
        return porting;
    }

    public void setPorting(boolean porting) {
        this.porting = porting;
    }

    public boolean isPublisher() {
        return publisher;
    }

    public void setPublisher(boolean publisher) {
        this.publisher = publisher;
    }

    public boolean isSupporting() {
        return supporting;
    }

    public void setSupporting(boolean supporting) {
        this.supporting = supporting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(company, i);
        parcel.writeByte((byte) (developer ? 1 : 0));
        parcel.writeByte((byte) (porting ? 1 : 0));
        parcel.writeByte((byte) (publisher ? 1 : 0));
        parcel.writeByte((byte) (supporting ? 1 : 0));
    }
}

