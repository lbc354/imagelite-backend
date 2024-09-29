package com.lucasbarros.imageliteapi.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_image")
@EntityListeners(AuditingEntityListener.class) // para @CreatedDate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(name = "name")
	private String name;
	@Column
	private Long size;
	@Column
	@Enumerated(EnumType.STRING)
	private ImageExtension extension;
	@Column
	@CreatedDate
	private LocalDateTime uploadDate;
	@Column
	private String tags;
	@Column
	@Lob // essa anotação diz que o campo é um arquivo
	private byte[] file;
	
	
	
	public String getFileName() {
		return getName().concat(".").concat(getExtension().name());
	}

}
