package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.PlayList;
import com.example.demo.entity.Song;
import com.example.demo.service.PlaylistService;
import com.example.demo.service.SongService;

@Controller
public class PlayListController {
	
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model)
	{
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute PlayList playlist)
	{
		//updating playlist table
		playlistService.addPlaylist(playlist);
		
		//updating song table
		List<Song> songList = playlist.getSongs();
		for(Song s:songList)
		{
			s.getPlaylists().add(playlist);
			//update song abject in db
			songService.updateSong(s);
		}
		return "admin";
	}
	
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model)
	{
		List<PlayList> allPlaylists=playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylists",allPlaylists);
		return "displayPlaylists";
	}
			
}
