/* This file is auto-generated from GuidedStepFragment.java.  DO NOT MODIFY. */

/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package android.support.v17.leanback.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.transition.TransitionHelper;
import android.support.v17.leanback.R;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidanceStylist.Guidance;
import android.support.v17.leanback.widget.GuidedAction;
import android.support.v17.leanback.widget.GuidedActionsStylist;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A GuidedStepSupportFragment is used to guide the user through a decision or series of decisions.
 * It is composed of a guidance view on the left and a view on the right containing a list of
 * possible actions.
 * <p>
 * <h3>Basic Usage</h3>
 * <p>
 * Clients of GuidedStepSupportFragment must create a custom subclass to attach to their Activities.
 * This custom subclass provides the information necessary to construct the user interface and
 * respond to user actions. At a minimum, subclasses should override:
 * <ul>
 * <li>{@link #onCreateGuidance}, to provide instructions to the user</li>
 * <li>{@link #onCreateActions}, to provide a set of {@link GuidedAction}s the user can take</li>
 * <li>{@link #onGuidedActionClicked}, to respond to those actions</li>
 * </ul>
 * <p>
 * Clients use following helper functions to add GuidedStepSupportFragment to Activity or FragmentManager:
 * <ul>
 * <li>{@link #addAsRoot(FragmentActivity, GuidedStepSupportFragment, int)}, to be called during Activity onCreate,
 * adds GuidedStepSupportFragment as the first Fragment in activity.</li>
 * <li>{@link #add(FragmentManager, GuidedStepSupportFragment)} or {@link #add(FragmentManager,
 * GuidedStepSupportFragment, int)}, to add GuidedStepSupportFragment on top of existing Fragments or
 * replacing existing GuidedStepSupportFragment when moving forward to next step.</li>
 * <li>{@link #finishGuidedStepSupportFragments()} can either finish the activity or pop all
 * GuidedStepSupportFragment from stack.
 * <li>If app chooses not to use the helper function, it is the app's responsibility to call
 * {@link #setUiStyle(int)} to select fragment transition and remember the stack entry where it
 * need pops to.
 * </ul>
 * <h3>Theming and Stylists</h3>
 * <p>
 * GuidedStepSupportFragment delegates its visual styling to classes called stylists. The {@link
 * GuidanceStylist} is responsible for the left guidance view, while the {@link
 * GuidedActionsStylist} is responsible for the right actions view. The stylists use theme
 * attributes to derive values associated with the presentation, such as colors, animations, etc.
 * Most simple visual aspects of GuidanceStylist and GuidedActionsStylist can be customized
 * via theming; see their documentation for more information.
 * <p>
 * GuidedStepSupportFragments must have access to an appropriate theme in order for the stylists to
 * function properly.  Specifically, the fragment must receive {@link
 * android.support.v17.leanback.R.style#Theme_Leanback_GuidedStep}, or a theme whose parent is
 * is set to that theme. Themes can be provided in one of three ways:
 * <ul>
 * <li>The simplest way is to set the theme for the host Activity to the GuidedStep theme or a
 * theme that derives from it.</li>
 * <li>If the Activity already has a theme and setting its parent theme is inconvenient, the
 * existing Activity theme can have an entry added for the attribute {@link
 * android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedStepTheme}. If present,
 * this theme will be used by GuidedStepSupportFragment as an overlay to the Activity's theme.</li>
 * <li>Finally, custom subclasses of GuidedStepSupportFragment may provide a theme through the {@link
 * #onProvideTheme} method. This can be useful if a subclass is used across multiple
 * Activities.</li>
 * </ul>
 * <p>
 * If the theme is provided in multiple ways, the onProvideTheme override has priority, followed by
 * the Activty's theme.  (Themes whose parent theme is already set to the guided step theme do not
 * need to set the guidedStepTheme attribute; if set, it will be ignored.)
 * <p>
 * If themes do not provide enough customizability, the stylists themselves may be subclassed and
 * provided to the GuidedStepSupportFragment through the {@link #onCreateGuidanceStylist} and {@link
 * #onCreateActionsStylist} methods.  The stylists have simple hooks so that subclasses
 * may override layout files; subclasses may also have more complex logic to determine styling.
 * <p>
 * <h3>Guided sequences</h3>
 * <p>
 * GuidedStepSupportFragments can be grouped together to provide a guided sequence. GuidedStepSupportFragments
 * grouped as a sequence use custom animations provided by {@link GuidanceStylist} and
 * {@link GuidedActionsStylist} (or subclasses) during transitions between steps. Clients
 * should use {@link #add} to place subsequent GuidedFragments onto the fragment stack so that
 * custom animations are properly configured. (Custom animations are triggered automatically when
 * the fragment stack is subsequently popped by any normal mechanism.)
 * <p>
 * <i>Note: Currently GuidedStepSupportFragments grouped in this way must all be defined programmatically,
 * rather than in XML. This restriction may be removed in the future.</i>
 *
 * @attr ref android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedStepTheme
 * @attr ref android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedStepBackground
 * @attr ref android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedActionContentWidthWeight
 * @attr ref android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedActionContentWidthWeightTwoPanels
 * @attr ref android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedActionsBackground
 * @attr ref android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedActionsBackgroundDark
 * @attr ref android.support.v17.leanback.R.styleable#LeanbackGuidedStepTheme_guidedActionsElevation
 * @see GuidanceStylist
 * @see GuidanceStylist.Guidance
 * @see GuidedAction
 * @see GuidedActionsStylist
 */
public class GuidedStepSupportFragment extends Fragment implements GuidedActionAdapter.ClickListener,
        GuidedActionAdapter.FocusListener {

    private static final String TAG_LEAN_BACK_ACTIONS_FRAGMENT = "leanBackGuidedStepSupportFragment";
    private static final String EXTRA_ACTION_SELECTED_INDEX = "selectedIndex";

    private static final String ENTRY_NAME_REPLACE = "GuidedStepDefault";

    private static final String ENTRY_NAME_ENTRANCE = "GuidedStepEntrance";

    /**
     * Fragment argument name for UI style.  The argument value is persisted in fragment state.
     * The value is initially {@link #UI_STYLE_ENTRANCE} and might be changed in one of the three
     * helper functions:
     * <ul>
     * <li>{@link #addAsRoot(FragmentActivity, GuidedStepSupportFragment, int)}</li>
     * <li>{@link #add(FragmentManager, GuidedStepSupportFragment)} or {@link #add(FragmentManager,
     * GuidedStepSupportFragment, int)}</li>
     * </ul>
     * <p>
     * Argument value can be either:
     * <ul>
     * <li>{@link #UI_STYLE_REPLACE}</li>
     * <li>{@link #UI_STYLE_ENTRANCE}</li>
     * <li>{@link #UI_STYLE_ACTIVITY_ROOT}</li>
     * </ul>
     */
    public static final String EXTRA_UI_STYLE = "uiStyle";

    /**
     * This is the case that we use GuidedStepSupportFragment to replace another existing
     * GuidedStepSupportFragment when moving forward to next step. Default behavior of this style is:
     * <ul>
     * <li>Enter transition slides in from END(right), exit transition same as
     * {@link #UI_STYLE_ENTRANCE}.
     * </li>
     * </ul>
     */
    public static final int UI_STYLE_REPLACE = 0;

    /**
     * Default value for argument {@link #EXTRA_UI_STYLE}. The default value is assigned in
     * GuidedStepSupportFragment constructor. This is the case that we show GuidedStepSupportFragment on top of
     * other content. The default behavior of this style:
     * <ul>
     * <li>Enter transition slides in from two sides, exit transition slide out to START(left).
     * Background will be faded in. Note: Changing exit transition by UI style is not working
     * because fragment transition asks for exit transition before UI style is restored in Fragment
     * .onCreate().</li>
     * </ul>
     */
    public static final int UI_STYLE_ENTRANCE = 1;

    /**
     * One possible value of argument {@link #EXTRA_UI_STYLE}. This is the case that we show first
     * GuidedStepSupportFragment in a separate activity. The default behavior of this style:
     * <ul>
     * <li>Enter transition is assigned null (will rely on activity transition), exit transition is
     * same as {@link #UI_STYLE_ENTRANCE}. Note: Changing exit transition by UI style is not working
     * because fragment transition asks for exit transition before UI style is restored in
     * Fragment.onCreate().</li>
     * </ul>
     */
    public static final int UI_STYLE_ACTIVITY_ROOT = 2;

    private static final String TAG = "GuidedStepSupportFragment";
    private static final boolean DEBUG = false;

    private int mTheme;
    private ContextThemeWrapper mThemeWrapper;
    private GuidanceStylist mGuidanceStylist;
    private GuidedActionsStylist mActionsStylist;
    private GuidedActionsStylist mButtonActionsStylist;
    private GuidedActionAdapter mAdapter;
    private GuidedActionAdapter mButtonAdapter;
    private GuidedActionAdapterGroup mAdapterGroup;
    private List<GuidedAction> mActions = new ArrayList<GuidedAction>();
    private List<GuidedAction> mButtonActions = new ArrayList<GuidedAction>();
    private int mSelectedIndex = -1;
    private int mButtonSelectedIndex = -1;

    public GuidedStepSupportFragment() {
        // We need to supply the theme before any potential call to onInflate in order
        // for the defaulting to work properly.
        mTheme = onProvideTheme();
        mGuidanceStylist = onCreateGuidanceStylist();
        mActionsStylist = onCreateActionsStylist();
        mButtonActionsStylist = onCreateButtonActionsStylist();
        onProvideFragmentTransitions();
    }

    /**
     * Creates the presenter used to style the guidance panel. The default implementation returns
     * a basic GuidanceStylist.
     * @return The GuidanceStylist used in this fragment.
     */
    public GuidanceStylist onCreateGuidanceStylist() {
        return new GuidanceStylist();
    }

    /**
     * Creates the presenter used to style the guided actions panel. The default implementation
     * returns a basic GuidedActionsStylist.
     * @return The GuidedActionsStylist used in this fragment.
     */
    public GuidedActionsStylist onCreateActionsStylist() {
        return new GuidedActionsStylist();
    }

    /**
     * Creates the presenter used to style a sided actions panel for button only.
     * The default implementation returns a basic GuidedActionsStylist.
     * @return The GuidedActionsStylist used in this fragment.
     */
    public GuidedActionsStylist onCreateButtonActionsStylist() {
        return new GuidedActionsStylist();
    }

    /**
     * Returns the theme used for styling the fragment. The default returns -1, indicating that the
     * host Activity's theme should be used.
     * @return The theme resource ID of the theme to use in this fragment, or -1 to use the
     * host Activity's theme.
     */
    public int onProvideTheme() {
        return -1;
    }

    /**
     * Returns the information required to provide guidance to the user. This hook is called during
     * {@link #onCreateView}.  May be overridden to return a custom subclass of {@link
     * GuidanceStylist.Guidance} for use in a subclass of {@link GuidanceStylist}. The default
     * returns a Guidance object with empty fields; subclasses should override.
     * @param savedInstanceState The saved instance state from onCreateView.
     * @return The Guidance object representing the information used to guide the user.
     */
    public @NonNull Guidance onCreateGuidance(Bundle savedInstanceState) {
        return new Guidance("", "", "", null);
    }

    /**
     * Fills out the set of actions available to the user. This hook is called during {@link
     * #onCreate}. The default leaves the list of actions empty; subclasses should override.
     * @param actions A non-null, empty list ready to be populated.
     * @param savedInstanceState The saved instance state from onCreate.
     */
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
    }

    /**
     * Fills out the set of actions shown at right available to the user. This hook is called during
     * {@link #onCreate}. The default leaves the list of actions empty; subclasses may override.
     * @param actions A non-null, empty list ready to be populated.
     * @param savedInstanceState The saved instance state from onCreate.
     */
    public void onCreateButtonActions(@NonNull List<GuidedAction> actions,
            Bundle savedInstanceState) {
    }

    /**
     * Callback invoked when an action is taken by the user. Subclasses should override in
     * order to act on the user's decisions.
     * @param action The chosen action.
     */
    @Override
    public void onGuidedActionClicked(GuidedAction action) {
    }

    /**
     * Callback invoked when an action is focused (made to be the current selection) by the user.
     */
    @Override
    public void onGuidedActionFocused(GuidedAction action) {
    }

    /**
     * Callback invoked when an action's title or description has been edited.
     * Override {@link #onGuidedActionEditedAndProceed(GuidedAction)} instead of app wants to
     * control the next action to focus on.
     */
    public void onGuidedActionEdited(GuidedAction action) {
    }

    /**
     * Callback invoked when an action's title or description has been edited.  Default
     * implementation calls {@link #onGuidedActionEdited(GuidedAction)} and returns
     * {@link GuidedAction#ACTION_ID_NEXT}.
     *
     * @param action The action that has been edited.
     * @return ID of the action will be focused or {@link GuidedAction#ACTION_ID_NEXT},
     * {@link GuidedAction#ACTION_ID_CURRENT}.
     */
    public long onGuidedActionEditedAndProceed(GuidedAction action) {
        onGuidedActionEdited(action);
        return GuidedAction.ACTION_ID_NEXT;
    }

    /**
     * Adds the specified GuidedStepSupportFragment to the fragment stack, replacing any existing
     * GuidedStepSupportFragments in the stack, and configuring the fragment-to-fragment custom
     * transitions.  A backstack entry is added, so the fragment will be dismissed when BACK key
     * is pressed.
     * <li>If current fragment on stack is GuidedStepSupportFragment: assign {@link #UI_STYLE_REPLACE}
     * <li>If current fragment on stack is not GuidedStepSupportFragment: assign {@link #UI_STYLE_ENTRANCE}
     * <p>
     * Note: currently fragments added using this method must be created programmatically rather
     * than via XML.
     * @param fragmentManager The FragmentManager to be used in the transaction.
     * @param fragment The GuidedStepSupportFragment to be inserted into the fragment stack.
     * @return The ID returned by the call FragmentTransaction.replace.
     */
    public static int add(FragmentManager fragmentManager, GuidedStepSupportFragment fragment) {
        return add(fragmentManager, fragment, android.R.id.content);
    }

    /**
     * Adds the specified GuidedStepSupportFragment to the fragment stack, replacing any existing
     * GuidedStepSupportFragments in the stack, and configuring the fragment-to-fragment custom
     * transitions.  A backstack entry is added, so the fragment will be dismissed when BACK key
     * is pressed.
     * <li>If current fragment on stack is GuidedStepSupportFragment: assign {@link #UI_STYLE_REPLACE} and
     * {@link #onAddSharedElementTransition(FragmentTransaction, GuidedStepSupportFragment)} will be called
     * to perform shared element transition between GuidedStepSupportFragments.
     * <li>If current fragment on stack is not GuidedStepSupportFragment: assign {@link #UI_STYLE_ENTRANCE}
     * <p>
     * Note: currently fragments added using this method must be created programmatically rather
     * than via XML.
     * @param fragmentManager The FragmentManager to be used in the transaction.
     * @param fragment The GuidedStepSupportFragment to be inserted into the fragment stack.
     * @param id The id of container to add GuidedStepSupportFragment, can be android.R.id.content.
     * @return The ID returned by the call FragmentTransaction.replace.
     */
    public static int add(FragmentManager fragmentManager, GuidedStepSupportFragment fragment, int id) {
        GuidedStepSupportFragment current = getCurrentGuidedStepSupportFragment(fragmentManager);
        boolean inGuidedStep = current != null;
        FragmentTransaction ft = fragmentManager.beginTransaction();

        fragment.setUiStyle(inGuidedStep ? UI_STYLE_REPLACE : UI_STYLE_ENTRANCE);
        ft.addToBackStack(fragment.generateStackEntryName());
        if (current != null) {
            fragment.onAddSharedElementTransition(ft, current);
        }
        return ft.replace(id, fragment, TAG_LEAN_BACK_ACTIONS_FRAGMENT).commit();
    }

    /**
     * Called when this fragment is added to FragmentTransaction with {@link #UI_STYLE_REPLACE} (aka
     * when the GuidedStepSupportFragment replacing an existing GuidedStepSupportFragment). Default implementation
     * establishes connections between action background views to morph action background bounds
     * change from disappearing GuidedStepSupportFragment into this GuidedStepSupportFragment. The default
     * implementation heavily relies on {@link GuidedActionsStylist}'s layout, app may override this
     * method when modifying the default layout of {@link GuidedActionsStylist}.
     *
     * @see GuidedActionsStylist
     * @see #onProvideFragmentTransitions()
     * @param ft The FragmentTransaction to add shared element.
     * @param disappearing The disappearing fragment.
     */
    protected void onAddSharedElementTransition(FragmentTransaction ft, GuidedStepSupportFragment
            disappearing) {
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.action_fragment_root), "action_fragment_root");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.action_fragment_background), "action_fragment_background");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.action_fragment), "action_fragment");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_root), "guidedactions_root");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_selector), "guidedactions_selector");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_content), "guidedactions_content");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_list_background), "guidedactions_list_background");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_root2), "guidedactions_root2");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_selector2), "guidedactions_selector2");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_content2), "guidedactions_content2");
        TransitionHelper.addSharedElement(ft, disappearing.getView().findViewById(
                R.id.guidedactions_list_background2), "guidedactions_list_background2");
    }

    /**
     * Returns BackStackEntry name for the GuidedStepSupportFragment or empty String if no entry is
     * associated.  Note {@link #UI_STYLE_ACTIVITY_ROOT} will return empty String.  The method
     * returns undefined value if the fragment is not in FragmentManager.
     * @return BackStackEntry name for the GuidedStepSupportFragment or empty String if no entry is
     * associated.
     */
    public String generateStackEntryName() {
        return generateStackEntryName(getUiStyle(), getClass());
    }

    /**
     * Generates BackStackEntry name for GuidedStepSupportFragment class or empty String if no entry is
     * associated.  Note {@link #UI_STYLE_ACTIVITY_ROOT} is not allowed and returns empty String.
     * @param uiStyle {@link #UI_STYLE_REPLACE} or {@link #UI_STYLE_ENTRANCE}
     * @return BackStackEntry name for the GuidedStepSupportFragment or empty String if no entry is
     * associated.
     */
    public static String generateStackEntryName(int uiStyle, Class guidedStepFragmentClass) {
        if (!GuidedStepSupportFragment.class.isAssignableFrom(guidedStepFragmentClass)) {
            return "";
        }
        switch (uiStyle) {
        case UI_STYLE_REPLACE:
            return ENTRY_NAME_REPLACE + guidedStepFragmentClass.getName();
        case UI_STYLE_ENTRANCE:
            return ENTRY_NAME_ENTRANCE + guidedStepFragmentClass.getName();
        case UI_STYLE_ACTIVITY_ROOT:
        default:
            return "";
        }
    }

    /**
     * Returns true if the backstack represents GuidedStepSupportFragment with {@link #UI_STYLE_ENTRANCE};
     * false otherwise.
     * @param backStackEntryName Name of BackStackEntry.
     * @return True if the backstack represents GuidedStepSupportFragment with {@link #UI_STYLE_ENTRANCE};
     * false otherwise.
     */
    public static boolean isUiStyleEntrance(String backStackEntryName) {
        return backStackEntryName != null && backStackEntryName.startsWith(ENTRY_NAME_ENTRANCE);
    }

    /**
     * Returns true if the backstack represents GuidedStepSupportFragment with {@link #UI_STYLE_REPLACE};
     * false otherwise.
     * @param backStackEntryName Name of BackStackEntry.
     * @return True if the backstack represents GuidedStepSupportFragment with {@link #UI_STYLE_REPLACE};
     * false otherwise.
     */
    public static boolean isUiStyleDefault(String backStackEntryName) {
        return backStackEntryName != null && backStackEntryName.startsWith(ENTRY_NAME_REPLACE);
    }

    /**
     * Extract Class name from BackStackEntry name.
     * @param backStackEntryName Name of BackStackEntry.
     * @return Class name of GuidedStepSupportFragment.
     */
    public static String getGuidedStepSupportFragmentClassName(String backStackEntryName) {
        if (backStackEntryName.startsWith(ENTRY_NAME_REPLACE)) {
            return backStackEntryName.substring(ENTRY_NAME_REPLACE.length());
        } else if (backStackEntryName.startsWith(ENTRY_NAME_ENTRANCE)) {
            return backStackEntryName.substring(ENTRY_NAME_ENTRANCE.length());
        } else {
            return "";
        }
    }

    /**
     * Adds the specified GuidedStepSupportFragment as content of Activity; no backstack entry is added so
     * the activity will be dismissed when BACK key is pressed.
     * {@link #UI_STYLE_ACTIVITY_ROOT} is assigned.
     *
     * Note: currently fragments added using this method must be created programmatically rather
     * than via XML.
     * @param activity The Activity to be used to insert GuidedstepFragment.
     * @param fragment The GuidedStepSupportFragment to be inserted into the fragment stack.
     * @param id The id of container to add GuidedStepSupportFragment, can be android.R.id.content.
     * @return The ID returned by the call FragmentTransaction.replace.
     */
    public static int addAsRoot(FragmentActivity activity, GuidedStepSupportFragment fragment, int id) {
        // Workaround b/23764120: call getDecorView() to force requestFeature of ActivityTransition.
        activity.getWindow().getDecorView();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragment.setUiStyle(UI_STYLE_ACTIVITY_ROOT);
        return ft.replace(id, fragment, TAG_LEAN_BACK_ACTIONS_FRAGMENT).commit();
    }

    /**
     * Returns the current GuidedStepSupportFragment on the fragment transaction stack.
     * @return The current GuidedStepSupportFragment, if any, on the fragment transaction stack.
     */
    public static GuidedStepSupportFragment getCurrentGuidedStepSupportFragment(FragmentManager fm) {
        Fragment f = fm.findFragmentByTag(TAG_LEAN_BACK_ACTIONS_FRAGMENT);
        if (f instanceof GuidedStepSupportFragment) {
            return (GuidedStepSupportFragment) f;
        }
        return null;
    }

    /**
     * Returns the GuidanceStylist that displays guidance information for the user.
     * @return The GuidanceStylist for this fragment.
     */
    public GuidanceStylist getGuidanceStylist() {
        return mGuidanceStylist;
    }

    /**
     * Returns the GuidedActionsStylist that displays the actions the user may take.
     * @return The GuidedActionsStylist for this fragment.
     */
    public GuidedActionsStylist getGuidedActionsStylist() {
        return mActionsStylist;
    }

    /**
     * Returns the list of button GuidedActions that the user may take in this fragment.
     * @return The list of button GuidedActions for this fragment.
     */
    public List<GuidedAction> getButtonActions() {
        return mButtonActions;
    }

    /**
     * Find button GuidedAction by Id.
     * @param id  Id of the button action to search.
     * @return  GuidedAction object or null if not found.
     */
    public GuidedAction findButtonActionById(long id) {
        int index = findButtonActionPositionById(id);
        return index >= 0 ? mButtonActions.get(index) : null;
    }

    /**
     * Find button GuidedAction position in array by Id.
     * @param id  Id of the button action to search.
     * @return  position of GuidedAction object in array or -1 if not found.
     */
    public int findButtonActionPositionById(long id) {
        if (mButtonActions != null) {
            for (int i = 0; i < mButtonActions.size(); i++) {
                GuidedAction action = mButtonActions.get(i);
                if (mButtonActions.get(i).getId() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the GuidedActionsStylist that displays the button actions the user may take.
     * @return The GuidedActionsStylist for this fragment.
     */
    public GuidedActionsStylist getGuidedButtonActionsStylist() {
        return mButtonActionsStylist;
    }

    /**
     * Sets the list of button GuidedActions that the user may take in this fragment.
     * @param actions The list of button GuidedActions for this fragment.
     */
    public void setButtonActions(List<GuidedAction> actions) {
        mButtonActions = actions;
        if (mButtonAdapter != null) {
            mButtonAdapter.setActions(mButtonActions);
        }
    }

    /**
     * Notify an button action has changed and update its UI.
     * @param position Position of the button GuidedAction in array.
     */
    public void notifyButtonActionChanged(int position) {
        if (mButtonAdapter != null) {
            mButtonAdapter.notifyItemChanged(position);
        }
    }

    /**
     * Returns the view corresponding to the button action at the indicated position in the list of
     * actions for this fragment.
     * @param position The integer position of the button action of interest.
     * @return The View corresponding to the button action at the indicated position, or null if
     * that action is not currently onscreen.
     */
    public View getButtonActionItemView(int position) {
        final RecyclerView.ViewHolder holder = mButtonActionsStylist.getActionsGridView()
                    .findViewHolderForPosition(position);
        return holder == null ? null : holder.itemView;
    }

    /**
     * Scrolls the action list to the position indicated, selecting that button action's view.
     * @param position The integer position of the button action of interest.
     */
    public void setSelectedButtonActionPosition(int position) {
        mButtonActionsStylist.getActionsGridView().setSelectedPosition(position);
    }

    /**
     * Returns the position if the currently selected button GuidedAction.
     * @return position The integer position of the currently selected button action.
     */
    public int getSelectedButtonActionPosition() {
        return mButtonActionsStylist.getActionsGridView().getSelectedPosition();
    }

    /**
     * Returns the list of GuidedActions that the user may take in this fragment.
     * @return The list of GuidedActions for this fragment.
     */
    public List<GuidedAction> getActions() {
        return mActions;
    }

    /**
     * Find GuidedAction by Id.
     * @param id  Id of the action to search.
     * @return  GuidedAction object or null if not found.
     */
    public GuidedAction findActionById(long id) {
        int index = findActionPositionById(id);
        return index >= 0 ? mActions.get(index) : null;
    }

    /**
     * Find GuidedAction position in array by Id.
     * @param id  Id of the action to search.
     * @return  position of GuidedAction object in array or -1 if not found.
     */
    public int findActionPositionById(long id) {
        if (mActions != null) {
            for (int i = 0; i < mActions.size(); i++) {
                GuidedAction action = mActions.get(i);
                if (mActions.get(i).getId() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Sets the list of GuidedActions that the user may take in this fragment.
     * @param actions The list of GuidedActions for this fragment.
     */
    public void setActions(List<GuidedAction> actions) {
        mActions = actions;
        if (mAdapter != null) {
            mAdapter.setActions(mActions);
        }
    }

    /**
     * Notify an action has changed and update its UI.
     * @param position Position of the GuidedAction in array.
     */
    public void notifyActionChanged(int position) {
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(position);
        }
    }

    /**
     * Returns the view corresponding to the action at the indicated position in the list of
     * actions for this fragment.
     * @param position The integer position of the action of interest.
     * @return The View corresponding to the action at the indicated position, or null if that
     * action is not currently onscreen.
     */
    public View getActionItemView(int position) {
        final RecyclerView.ViewHolder holder = mActionsStylist.getActionsGridView()
                    .findViewHolderForPosition(position);
        return holder == null ? null : holder.itemView;
    }

    /**
     * Scrolls the action list to the position indicated, selecting that action's view.
     * @param position The integer position of the action of interest.
     */
    public void setSelectedActionPosition(int position) {
        mActionsStylist.getActionsGridView().setSelectedPosition(position);
    }

    /**
     * Returns the position if the currently selected GuidedAction.
     * @return position The integer position of the currently selected action.
     */
    public int getSelectedActionPosition() {
        return mActionsStylist.getActionsGridView().getSelectedPosition();
    }

    /**
     * Called by Constructor to provide fragment transitions.  The default implementation assigns
     * transitions based on {@link #getUiStyle()}:
     * <ul>
     * <li> {@link #UI_STYLE_REPLACE} Slide from/to end(right) for enter transition, slide from/to
     * start(left) for exit transition, shared element enter transition is set to ChangeBounds.
     * <li> {@link #UI_STYLE_ENTRANCE} Enter transition is set to slide from both sides, exit
     * transition is same as {@link #UI_STYLE_REPLACE}, no shared element enter transition.
     * <li> {@link #UI_STYLE_ACTIVITY_ROOT} Enter transition is set to null and app should rely on
     * activity transition, exit transition is same as {@link #UI_STYLE_REPLACE}, no shared element
     * enter transition.
     * </ul>
     * <p>
     * The default implementation heavily relies on {@link GuidedActionsStylist} and
     * {@link GuidanceStylist} layout, app may override this method when modifying the default
     * layout of {@link GuidedActionsStylist} or {@link GuidanceStylist}.
     * <p>
     * TIP: because the fragment view is removed during fragment transition, in general app cannot
     * use two Visibility transition together. Workaround is to create your own Visibility
     * transition that controls multiple animators (e.g. slide and fade animation in one Transition
     * class).
     */
    protected void onProvideFragmentTransitions() {
        if (Build.VERSION.SDK_INT >= 21) {
            final int uiStyle = getUiStyle();
            if (uiStyle == UI_STYLE_REPLACE) {
                Object enterTransition = TransitionHelper.createFadeAndShortSlide(Gravity.END);
                TransitionHelper.exclude(enterTransition, R.id.guidedstep_background, true);
                TransitionHelper.setEnterTransition(this, enterTransition);

                Object changeBounds = TransitionHelper.createChangeBounds(false);
                TransitionHelper.setSharedElementEnterTransition(this, changeBounds);
            } else if (uiStyle == UI_STYLE_ENTRANCE) {
                Object fade = TransitionHelper.createFadeTransition(TransitionHelper.FADE_IN |
                        TransitionHelper.FADE_OUT);
                TransitionHelper.include(fade, R.id.guidedstep_background);
                Object slide = TransitionHelper.createFadeAndShortSlide(Gravity.END |
                        Gravity.START);
                TransitionHelper.include(slide, R.id.content_fragment);
                TransitionHelper.include(slide, R.id.action_fragment_root);
                Object enterTransition = TransitionHelper.createTransitionSet(false);
                TransitionHelper.addTransition(enterTransition, fade);
                TransitionHelper.addTransition(enterTransition, slide);
                TransitionHelper.setEnterTransition(this, enterTransition);

                // No shared element transition
                TransitionHelper.setSharedElementEnterTransition(this, null);
            } else if (uiStyle == UI_STYLE_ACTIVITY_ROOT) {
                // for Activity root, we dont need enter transition, use activity transition
                TransitionHelper.setEnterTransition(this, null);
                // No shared element transition
                TransitionHelper.setSharedElementEnterTransition(this, null);
            }
            // exitTransition is same for all style
            Object exitTransition = TransitionHelper.createFadeAndShortSlide(Gravity.START);
            TransitionHelper.exclude(exitTransition, R.id.guidedstep_background, true);
            TransitionHelper.setExitTransition(this, exitTransition);
        }
    }

    /**
     * Called by onCreateView to inflate background view.  Default implementation loads view
     * from {@link R.layout#lb_guidedstep_background} which holds a reference to
     * guidedStepBackground.
     * @param inflater LayoutInflater to load background view.
     * @param container Parent view of background view.
     * @param savedInstanceState
     * @return Created background view or null if no background.
     */
    public View onCreateBackgroundView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lb_guidedstep_background, container, false);
    }

    /**
     * Set UI style to fragment arguments. Default value is {@link #UI_STYLE_ENTRANCE} when fragment
     * is first initialized. UI style is used to choose different fragment transition animations and
     * determine if this is the first GuidedStepSupportFragment on backstack. In most cases app does not
     * directly call this method, app calls helper function
     * {@link #add(FragmentManager, GuidedStepSupportFragment, int)}. However if the app creates Fragment
     * transaction and controls backstack by itself, it would need call setUiStyle() to select the
     * fragment transition to use.
     *
     * @param style {@link #UI_STYLE_ACTIVITY_ROOT} {@link #UI_STYLE_REPLACE} or
     *        {@link #UI_STYLE_ENTRANCE}.
     */
    public void setUiStyle(int style) {
        int oldStyle = getUiStyle();
        Bundle arguments = getArguments();
        boolean isNew = false;
        if (arguments == null) {
            arguments = new Bundle();
            isNew = true;
        }
        arguments.putInt(EXTRA_UI_STYLE, style);
        // call setArgument() will validate if the fragment is already added.
        if (isNew) {
            setArguments(arguments);
        }
        if (style != oldStyle) {
            onProvideFragmentTransitions();
        }
    }

    /**
     * Read UI style from fragment arguments.  Default value is {@link #UI_STYLE_ENTRANCE} when
     * fragment is first initialized.  UI style is used to choose different fragment transition
     * animations and determine if this is the first GuidedStepSupportFragment on backstack.
     *
     * @return {@link #UI_STYLE_ACTIVITY_ROOT} {@link #UI_STYLE_REPLACE} or
     * {@link #UI_STYLE_ENTRANCE}.
     * @see #onProvideFragmentTransitions()
     */
    public int getUiStyle() {
        Bundle b = getArguments();
        if (b == null) return UI_STYLE_ENTRANCE;
        return b.getInt(EXTRA_UI_STYLE, UI_STYLE_ENTRANCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.v(TAG, "onCreate");
        // Set correct transition from saved arguments.
        onProvideFragmentTransitions();
        Bundle state = (savedInstanceState != null) ? savedInstanceState : getArguments();
        if (state != null) {
            if (mSelectedIndex == -1) {
                mSelectedIndex = state.getInt(EXTRA_ACTION_SELECTED_INDEX, -1);
            }
        }
        ArrayList<GuidedAction> actions = new ArrayList<GuidedAction>();
        onCreateActions(actions, savedInstanceState);
        setActions(actions);
        ArrayList<GuidedAction> buttonActions = new ArrayList<GuidedAction>();
        onCreateButtonActions(buttonActions, savedInstanceState);
        setButtonActions(buttonActions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {
        mGuidanceStylist.onDestroyView();
        mActionsStylist.onDestroyView();
        mButtonActionsStylist.onDestroyView();
        mAdapter = null;
        mButtonAdapter = null;
        mAdapterGroup = null;
        super.onDestroyView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (DEBUG) Log.v(TAG, "onCreateView");

        resolveTheme();
        inflater = getThemeInflater(inflater);

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.lb_guidedstep_fragment,
                container, false);
        ViewGroup guidanceContainer = (ViewGroup) root.findViewById(R.id.content_fragment);
        ViewGroup actionContainer = (ViewGroup) root.findViewById(R.id.action_fragment);

        Guidance guidance = onCreateGuidance(savedInstanceState);
        View guidanceView = mGuidanceStylist.onCreateView(inflater, guidanceContainer, guidance);
        guidanceContainer.addView(guidanceView);

        View actionsView = mActionsStylist.onCreateView(inflater, actionContainer);
        actionContainer.addView(actionsView);

        View buttonActionsView = mButtonActionsStylist.onCreateView(inflater, actionContainer);
        mButtonActionsStylist.setAsButtonActions();
        actionContainer.addView(buttonActionsView);

        GuidedActionAdapter.EditListener editListener = new GuidedActionAdapter.EditListener() {

                @Override
                public void onImeOpen() {
                    runImeAnimations(true);
                }

                @Override
                public void onImeClose() {
                    runImeAnimations(false);
                }

                @Override
                public long onGuidedActionEdited(GuidedAction action) {
                    return GuidedStepSupportFragment.this.onGuidedActionEditedAndProceed(action);
                }
        };

        mAdapter = new GuidedActionAdapter(mActions, this, this, mActionsStylist);
        mButtonAdapter = new GuidedActionAdapter(mButtonActions, this, this, mButtonActionsStylist);
        mAdapterGroup = new GuidedActionAdapterGroup();
        mAdapterGroup.addAdpter(mAdapter);
        mAdapterGroup.addAdpter(mButtonAdapter);
        mAdapterGroup.setEditListener(editListener);

        mActionsStylist.getActionsGridView().setAdapter(mAdapter);
        mButtonActionsStylist.getActionsGridView().setAdapter(mButtonAdapter);
        if (mButtonActions.size() == 0) {
            // when there is no button actions, we dont need show the second panel, but keep
            // the width zero to run ChangeBounds transition.
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)
                    buttonActionsView.getLayoutParams();
            lp.weight = 0;
            buttonActionsView.setLayoutParams(lp);
        } else {
            // when there are two actions panel, we need adjust the weight of action to
            // guidedActionContentWidthWeightTwoPanels.
            Context ctx = mThemeWrapper != null ? mThemeWrapper : getActivity();
            TypedValue typedValue = new TypedValue();
            if (ctx.getTheme().resolveAttribute(R.attr.guidedActionContentWidthWeightTwoPanels,
                    typedValue, true)) {
                View actionsRoot = root.findViewById(R.id.action_fragment_root);
                float weight = typedValue.getFloat();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) actionsRoot
                        .getLayoutParams();
                lp.weight = weight;
                actionsRoot.setLayoutParams(lp);
            }
        }

        int pos = (mSelectedIndex >= 0 && mSelectedIndex < mActions.size()) ?
                mSelectedIndex : getFirstCheckedAction();
        setSelectedActionPosition(pos);

        setSelectedButtonActionPosition(0);

        View backgroundView = onCreateBackgroundView(inflater, root, savedInstanceState);
        if (backgroundView != null) {
            root.addView(backgroundView, 0);
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsStylist.getActionsGridView().requestFocus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_ACTION_SELECTED_INDEX,
                (mActionsStylist.getActionsGridView() != null) ?
                        getSelectedActionPosition() : mSelectedIndex);
    }

    private static boolean isGuidedStepTheme(Context context) {
        int resId = R.attr.guidedStepThemeFlag;
        TypedValue typedValue = new TypedValue();
        boolean found = context.getTheme().resolveAttribute(resId, typedValue, true);
        if (DEBUG) Log.v(TAG, "Found guided step theme flag? " + found);
        return found && typedValue.type == TypedValue.TYPE_INT_BOOLEAN && typedValue.data != 0;
    }

    /**
     * Convenient method to close GuidedStepSupportFragments on top of other content or finish Activity if
     * GuidedStepSupportFragments were started in a separate activity.  Pops all stack entries including
     * {@link #UI_STYLE_ENTRANCE}; if {@link #UI_STYLE_ENTRANCE} is not found, finish the activity.
     * Note that this method must be paired with {@link #add(FragmentManager, GuidedStepSupportFragment,
     * int)} which sets up the stack entry name for finding which fragment we need to pop back to.
     */
    public void finishGuidedStepSupportFragments() {
        final FragmentManager fragmentManager = getFragmentManager();
        final int entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount > 0) {
            for (int i = entryCount - 1; i >= 0; i--) {
                BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
                if (isUiStyleEntrance(entry.getName())) {
                    GuidedStepSupportFragment top = getCurrentGuidedStepSupportFragment(fragmentManager);
                    if (top != null) {
                        top.setUiStyle(UI_STYLE_ENTRANCE);
                    }
                    fragmentManager.popBackStack(entry.getId(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return;
                }
            }
        }
        ActivityCompat.finishAfterTransition(getActivity());
    }

    /**
     * Convenient method to pop to fragment with Given class.
     * @param  guidedStepFragmentClass  Name of the Class of GuidedStepSupportFragment to pop to.
     * @param flags Either 0 or {@link FragmentManager#POP_BACK_STACK_INCLUSIVE}.
     */
    public void popBackStackToGuidedStepSupportFragment(Class guidedStepFragmentClass, int flags) {
        if (!GuidedStepSupportFragment.class.isAssignableFrom(guidedStepFragmentClass)) {
            return;
        }
        final FragmentManager fragmentManager = getFragmentManager();
        final int entryCount = fragmentManager.getBackStackEntryCount();
        String className = guidedStepFragmentClass.getName();
        if (entryCount > 0) {
            for (int i = entryCount - 1; i >= 0; i--) {
                BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
                String entryClassName = getGuidedStepSupportFragmentClassName(entry.getName());
                if (className.equals(entryClassName)) {
                    fragmentManager.popBackStack(entry.getId(), flags);
                    return;
                }
            }
        }
    }

    private void resolveTheme() {
        // Look up the guidedStepTheme in the currently specified theme.  If it exists,
        // replace the theme with its value.
        FragmentActivity activity = getActivity();
        if (mTheme == -1 && !isGuidedStepTheme(activity)) {
            // Look up the guidedStepTheme in the activity's currently specified theme.  If it
            // exists, replace the theme with its value.
            int resId = R.attr.guidedStepTheme;
            TypedValue typedValue = new TypedValue();
            boolean found = activity.getTheme().resolveAttribute(resId, typedValue, true);
            if (DEBUG) Log.v(TAG, "Found guided step theme reference? " + found);
            if (found) {
                ContextThemeWrapper themeWrapper =
                        new ContextThemeWrapper(activity, typedValue.resourceId);
                if (isGuidedStepTheme(themeWrapper)) {
                    mTheme = typedValue.resourceId;
                    mThemeWrapper = themeWrapper;
                } else {
                    found = false;
                    mThemeWrapper = null;
                }
            }
            if (!found) {
                Log.e(TAG, "GuidedStepSupportFragment does not have an appropriate theme set.");
            }
        } else if (mTheme != -1) {
            mThemeWrapper = new ContextThemeWrapper(activity, mTheme);
        }
    }

    private LayoutInflater getThemeInflater(LayoutInflater inflater) {
        if (mTheme == -1) {
            return inflater;
        } else {
            return inflater.cloneInContext(mThemeWrapper);
        }
    }

    private int getFirstCheckedAction() {
        for (int i = 0, size = mActions.size(); i < size; i++) {
            if (mActions.get(i).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    private void runImeAnimations(boolean entering) {
        ArrayList<Animator> animators = new ArrayList<Animator>();
        if (entering) {
            mGuidanceStylist.onImeAppearing(animators);
            mActionsStylist.onImeAppearing(animators);
            mButtonActionsStylist.onImeAppearing(animators);
        } else {
            mGuidanceStylist.onImeDisappearing(animators);
            mActionsStylist.onImeDisappearing(animators);
            mButtonActionsStylist.onImeDisappearing(animators);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.start();
    }

}
