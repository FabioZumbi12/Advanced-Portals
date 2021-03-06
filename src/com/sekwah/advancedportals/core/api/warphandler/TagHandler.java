package com.sekwah.advancedportals.core.api.warphandler;

import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

/**
 * If a tag can be used for any of them then either make it cast the target or if it doesnt need a target
 * then the exact same tag can be registered into both and ignore the portal info.
 *
 * Will probably make better documentation on how to do so or some tutorial videos though take a look at the source code
 * on github for how the current tags are added.
 *
 * Also not sure if its good practice or not in java however these all extend TagHandler so they can be accepted in 1
 * method nicer than if they didn't
 *
 * @author sekwah41
 */
public interface TagHandler {

    /**
     * The events for portal creation and destroying
     */
    interface Creation<T> extends TagHandler {

        /**
         * Example if the player does not have access to use the tag.
         *
         * @param player if null then created by the server or a plugin
         * @param argData
         * @throws PortalException message given is the reason the portal (or destination) cannot be made
         */
        void created(T target, PlayerContainer player, String argData) throws PortalException;

        /**
         * Example if the player does not have access to remove the portal or destination.
         *
         * @param player if null then removed by the server or a plugin
         * @param argData
         * @throws PortalException message given is the reason the portal cant be removed
         */
        void destroyed(T target, PlayerContainer player, String argData) throws PortalException;

    }

    /**
     * Order of activation
     * Portal and Desti:
     * preActivated
     * activated
     * postActivated
     *
     * Order of them combined:
     * Portal.preActivated
     * Portal.activated - when desti tag is hit (if listed) then next two actions are activated
     *  - Desti.preActivate
     *  - Desti.activate
     * Portal.postActivate - when desti tag is hit (if listed) then the next action is activated
     *  - Desti.postActivate
     *
     * @param <T>
     */
    interface Activation<T> extends TagHandler {

        /**
         * Activates before the main part of activation. This should be for prechecks e.g. if the player has enough
         * money before then taking the money in postActivated.
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void preActivated(T target, PlayerContainer player, ActivationData activeData, String argData) throws PortalException;

        /**
         * Activates after activation
         *
         * Any actions to do with player location should be done in activate
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void postActivated(T target, PlayerContainer player, ActivationData activeData, String argData);

        /**
         * Activates if the portal is allowed from preActivating. Should be used to set the intended warp location
         *
         * You should do some second checks if it can be dependent on the preActivate, the destination tags will also be
         * triggered here if a desti is listed.
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void activated(T target, PlayerContainer player, ActivationData activeData, String argData) throws PortalException;

    }

    interface TagStatus<T> extends TagHandler {

        /**
         * If the user has access to add the tag (this does not include being added on creation)
         *
         * @param player
         * @param argData
         * @return if the tag will be added.
         */
        boolean tagAdded(T target, PlayerContainer player, String argData) throws PortalException;

        /**
         * If the user has access to remove the tag (this does not include being added on destruction)
         *
         * @param player
         * @param argData
         * @return if the tag will be removed.
         */
        boolean tagRemoved(T target, PlayerContainer player, String argData) throws PortalException;

    }

}
