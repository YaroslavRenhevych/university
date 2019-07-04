package com.yrenh.university.processor;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrenh.university.entity.Lector;
import com.yrenh.university.repository.LectorRepository;

@Service
public class LectorGlobalSearch implements CommandProcessor {

	private static final Pattern pattern = Pattern.compile("^Global search by .+");
	private static final String LECTORS_NOT_FOUND = "Lectors not found!";
	private static final int INDEX_OF_SEARCH_STRING = 17;

	private LectorRepository lectorRepository;

	@Autowired
	public LectorGlobalSearch(LectorRepository lectorRepository) {
		this.lectorRepository = lectorRepository;
	}

	@Override
	public boolean acceptableCommand(String command) {
		return pattern.matcher(command).find();
	}

	@Override
	public String process(String command) {
		StringBuilder answer = new StringBuilder();
		String searchText = command.substring(INDEX_OF_SEARCH_STRING);
		List<Lector> lectors = lectorRepository.findByFirstNameContainingOrLastNameContaining(searchText, searchText);
		if(lectors.isEmpty()) {
			answer = answer.append(LECTORS_NOT_FOUND);
		} else {
			for(Lector lector: lectors) {
				answer.append(lector.getFirstName())
					.append(" ")
					.append(lector.getLastName())
					.append(",");
			}
		}
		return answer.toString();
	}
}
