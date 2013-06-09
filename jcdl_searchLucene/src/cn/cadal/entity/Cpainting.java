package cn.cadal.entity;

// Generated 2006-4-10 20:48:27 by Hibernate Tools 3.1.0 beta1JBIDERC2

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "PaintingInfo")
public class Cpainting implements java.io.Serializable {

	// Fields    
	private Integer paintingNO;

	private String paintingName;

	private String author;

	private String location;

	private String appearance;

	private String description;

	private String filepath;

	private Integer filesize;

	// Constructors

	/** default constructor */
	public Cpainting() {
	}

	/** minimal constructor */
	public Cpainting(Integer paintingNO) {
		this.paintingNO = paintingNO;
	}

	/** full constructor */
	public Cpainting(Integer paintingNO, String paintingName, String author,
			String location, String appearance, String description,
			String filepath, Integer filesize) {
		this.paintingNO = paintingNO;
		this.paintingName = paintingName;
		this.author = author;
		this.location = location;
		this.appearance = appearance;
		this.description = description;
		this.filepath = filepath;
		this.filesize = filesize;
	}

	// Property accessors
	@Id
	@Column (name="sID")
	public Integer getPaintingNO() {
		return this.paintingNO;
	}

	public void setPaintingNO(Integer paintingNO) {
		this.paintingNO = paintingNO;
	}
	
	@Column (name="PName", length=100)
	public String getPaintingName() {
		return this.paintingName;
	}

	public void setPaintingName(String paintingName) {
		this.paintingName = paintingName;
	}

	@Column (name="Author", length=3000)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column (name="Location", length=150)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column (name="SpeSize", length=3000)
	public String getAppearance() {
		return this.appearance;
	}

	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}

	@Column (name="Description", length=300)
	@Lob
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column (name="FilePath", length=150)
	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Column (name="FileSize")
	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

}
