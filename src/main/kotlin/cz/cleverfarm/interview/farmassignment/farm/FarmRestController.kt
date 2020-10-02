package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.common.API_ROOT_FARMS
import cz.cleverfarm.interview.farmassignment.common.FARM_ID_VARIABLE
import cz.cleverfarm.interview.farmassignment.common.FARM_ID_VARIABLE_NAME
import cz.cleverfarm.interview.farmassignment.common.FARM_NOT_FOUND
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping(API_ROOT_FARMS)
class FarmRestController @Autowired constructor(private val farmService: FarmService) {

    @ApiOperation("Adds new farm")
    @PutMapping
    fun addNewFarm(@Valid @RequestBody farm: FarmForm): FarmDto {
        return farmService.addNewFarm(farm)
    }

    @ApiOperation("Retrieves all farms and their fields")
    @GetMapping
    fun findAllFarms(
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(name = "pageSize", required = false, defaultValue = "10") pageSize: Int
    ): List<FarmDto> {
        return farmService.findAllFarms(page, pageSize)
    }

    @ApiOperation("Retrieves farm by ID")
    @GetMapping(FARM_ID_VARIABLE)
    fun findFarm(@PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID): FarmDto {
        return farmService.findFarmById(farmId, fetchFields = true)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, FARM_NOT_FOUND)
    }

    @ApiOperation("Updates farm", notes = "Only name and note can be updated")
    @PostMapping(FARM_ID_VARIABLE)
    fun updateFarm(
        @PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID,
        @Valid @RequestBody updatedFarm: FarmForm
    ): FarmDto {
        return farmService.updateFarm(farmId, updatedFarm)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, FARM_NOT_FOUND)
    }

    @ApiOperation("Deletes farm and all its fields")
    @DeleteMapping(FARM_ID_VARIABLE)
    fun deleteFarm(@PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID) {
        farmService.deleteFarm(farmId) || throw ResponseStatusException(HttpStatus.NOT_FOUND, FARM_NOT_FOUND)
    }
}