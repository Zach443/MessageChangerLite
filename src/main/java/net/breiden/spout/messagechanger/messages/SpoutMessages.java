/******************************************************************************
 * This file is part of MessageChanger (http://www.spout.org/).               *
 *                                                                            *
 * MessageChanger is licensed under the SpoutDev License Version 1.           *
 *                                                                            *
 * MessageChanger is free software: you can redistribute it and/or modify     *
 * it under the terms of the GNU Lesser General Public License as published by*
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * In addition, 180 days after any changes are published, you can use the     *
 * software, incorporating those changes, under the terms of the MIT license, *
 * as described in the SpoutDev License Version 1.                            *
 *                                                                            *
 * MessageChanger is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU Lesser General Public License for more details.                        *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License,  *
 * the MIT license and the SpoutDev License Version 1 along with this program.*
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.                                                 *
 ******************************************************************************/

/******************************************************************************
 * This file is part of MessageChanger (http://www.spout.org/).               *
 *                                                                            *
 * MessageChanger is licensed under the SpoutDev License Version 1.           *
 *                                                                            *
 * MessageChanger is free software: you can redistribute it and/or modify     *
 * it under the terms of the GNU Lesser General Public License as published by*
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * In addition, 180 days after any changes are published, you can use the     *
 * software, incorporating those changes, under the terms of the MIT license, *
 * as described in the SpoutDev License Version 1.                            *
 *                                                                            *
 * MessageChanger is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU Lesser General Public License for more details.                        *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License,  *
 * the MIT license and the SpoutDev License Version 1 along with this program.*
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.                                                 *
 ******************************************************************************/

package net.breiden.spout.messagechanger.messages;

import net.breiden.spout.messagechanger.enums.DEFAULT_EVENTS;
import net.breiden.spout.messagechanger.events.SpoutPlayerEvents;
import net.breiden.spout.messagechanger.helper.Messenger;
import net.breiden.spout.messagechanger.helper.file.DefaultMessagesHandler;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.player.Player;
import org.spout.api.plugin.CommonPlugin;

/**
 * Contains code to handle the Spout specific messages
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutMessages implements MessagesInterface {

    private DefaultMessagesHandler defaultMessagesHandler;

    private static SpoutMessages instance;

    public SpoutMessages (CommonPlugin main){
         instance = this;
         defaultMessagesHandler = new DefaultMessagesHandler(main);
        new SpoutPlayerEvents(main);
        // todo init the event handlers
    }

    public static SpoutMessages getInstance(){
        return instance;
    }


    @Override
    public String getNewMessage(String event, Player player, String defaultMessage) {
        DEFAULT_EVENTS defaultEvent = DEFAULT_EVENTS.valueOf(event);
        String message = defaultMessage;
        if (defaultEvent != null){
            message = defaultMessagesHandler.getMessage(getCategory(player),defaultEvent);
            if (message == null || message.equals("%(msg)")){
                message = defaultMessage;
            }

        }
        return Messenger.dictFormat(player, message);
    }

    public String getNewMessage(String event, Player player, Object[] defaultMessage){
        String message = "";
        for (Object obj : defaultMessage){
            if (obj instanceof ChatStyle){
                message = message + ((ChatStyle) obj).getName();
            } else {
                message = message + (String) obj;
            }
        }
        return getNewMessage(event, player, message);
    }


    private String getCategory(Player player) {
    		for (String category : defaultMessagesHandler.getCategoryOrder()) {
    			if (player.hasPermission("messagechanger.message." + category)) {
    				return category;
    			}
    		}
    		return "default";
    	}

}
