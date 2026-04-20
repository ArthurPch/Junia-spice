package com.jad.dto;

import java.util.List;

public record MachineOrderDTO(int idMachineTool, List<OrderDTO> orders) {
}
