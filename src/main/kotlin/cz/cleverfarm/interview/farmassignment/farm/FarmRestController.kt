package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.common.API_ROOT_FARMS
import cz.cleverfarm.interview.farmassignment.common.FARM_ID_VARIABLE
import cz.cleverfarm.interview.farmassignment.common.FARM_ID_VARIABLE_NAME
import cz.cleverfarm.interview.farmassignment.common.FARM_NOT_FOUND
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping(API_ROOT_FARMS)
class FarmRestController @Autowired constructor(private val farmService: FarmService) {

    @PutMapping
    fun addNewFarm(@RequestBody farm: FarmForm): FarmDto {
        return farmService.addNewFarm(farm)
    }

    @GetMapping
    fun findAllFarms(): List<FarmDto> {
        return farmService.findAllFarms()
    }

    @GetMapping(FARM_ID_VARIABLE)
    fun findFarm(@PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID): FarmDto {
        return farmService.findFarmById(farmId, fetchFields = true)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, FARM_NOT_FOUND)
    }

    @PostMapping(FARM_ID_VARIABLE)
    fun updateFarm(@PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID, @RequestBody updatedFarm: FarmForm): FarmDto {
        return farmService.updateFarm(farmId, updatedFarm)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, FARM_NOT_FOUND)
    }

    @DeleteMapping(FARM_ID_VARIABLE)
    fun deleteFarm(@PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID) {
        farmService.deleteFarm(farmId) || throw ResponseStatusException(HttpStatus.NOT_FOUND, FARM_NOT_FOUND)
    }


}