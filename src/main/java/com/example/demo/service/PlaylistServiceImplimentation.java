package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PlayList;
import com.example.demo.entity.Song;
import com.example.demo.repository.PlaylistRepository;

@Service
public class PlaylistServiceImplimentation implements PlaylistService {

	@Autowired
	PlaylistRepository repo;
	@Override
	public void addPlaylist(PlayList playlist) {
		repo.save(playlist);
		
	}
	@Override
	public List<PlayList> fetchAllPlaylists() {
		return repo.findAll();
	}

	

}
