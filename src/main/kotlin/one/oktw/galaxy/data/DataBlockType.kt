/*
 * OKTW Galaxy Project
 * Copyright (C) 2018-2018
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package one.oktw.galaxy.data

import com.google.common.reflect.TypeToken
import one.oktw.galaxy.block.enums.CustomBlocks
import one.oktw.galaxy.block.enums.CustomBlocks.DUMMY
import one.oktw.galaxy.block.enums.CustomBlocks.valueOf
import org.spongepowered.api.data.DataContainer
import org.spongepowered.api.data.DataHolder
import org.spongepowered.api.data.DataQuery
import org.spongepowered.api.data.DataView
import org.spongepowered.api.data.key.Key
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleEnumData
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleEnumData
import org.spongepowered.api.data.merge.MergeFunction
import org.spongepowered.api.data.persistence.AbstractDataBuilder
import org.spongepowered.api.data.value.mutable.Value
import java.util.*

class DataBlockType(type: CustomBlocks = DUMMY) :
    AbstractSingleEnumData<CustomBlocks, DataBlockType, DataBlockType.Immutable>(type, key, DUMMY) {
    companion object {
        val key: Key<Value<CustomBlocks>> = Key.builder()
            .type(object : TypeToken<Value<CustomBlocks>>() {})
            .id("block_type")
            .name("Block Type")
            .query(DataQuery.of("type"))
            .build()
    }

    override fun getContentVersion() = 1
    override fun asImmutable() = Immutable(value)
    override fun copy() = DataBlockType(value)

    override fun from(container: DataContainer): Optional<DataBlockType> {
        return if (container[key.query].isPresent) {
            value = valueOf(container.getString(key.query).get())
            Optional.of(this)
        } else {
            Optional.empty()
        }
    }

    override fun fill(dataHolder: DataHolder, overlap: MergeFunction): Optional<DataBlockType> {
        value = overlap.merge(this, dataHolder[DataBlockType::class.java].orElse(null)).value
        return Optional.of(this)
    }


    class Immutable(type: CustomBlocks = DUMMY) :
        AbstractImmutableSingleEnumData<CustomBlocks, Immutable, DataBlockType>(type, DUMMY, key) {
        override fun getContentVersion() = 1
        override fun asMutable() = DataBlockType((value))
    }

    class Builder : AbstractDataBuilder<DataBlockType>(DataBlockType::class.java, 1),
        DataManipulatorBuilder<DataBlockType, Immutable> {
        override fun create() = DataBlockType()
        override fun createFrom(dataHolder: DataHolder): Optional<DataBlockType> = create().fill(dataHolder)
        override fun buildContent(container: DataView) = create().from(container.copy())
    }
}
