package dto;

import java.util.Set;

public class GroupScheduleDto {
	private final ScheduleDto dto;
	private final Set<Integer> idSet;
	public GroupScheduleDto(ScheduleDto dto, Set<Integer> idSet) {
		this.dto = dto;
		this.idSet = idSet;
	}
	public ScheduleDto getDto() {
		return dto;
	}
	public Set<Integer> getIdSet() {
		return idSet;
	}
}
