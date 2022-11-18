public class UploadedImage {
    String name ;
    String imageURL ;

    public UploadedImage() {}

    public UploadedImage (String name , String imageURL) {
        if (name.trim().equals("")) {
            name="No name" ; }
        else {
            this.name=name ;
            this.imageURL=imageURL ; }
    }

    public String getName() { return this.name ; }

    public void setName (String newName) { this.name=newName ; }

    public String getImageURL() { return imageURL ; }

    public void setImageURL(String newURL) { this.imageURL = newURL ; }
}
